<?php
//*******************************************************************
////// PHP Corina Middleware
////// License: GPL
////// Author: Peter Brewer
////// E-Mail: p.brewer@cornell.edu
//////
////// Requirements : PHP >= 5.0
//////*******************************************************************

class auth 
{
  var $securityuserid = NULL;
  var $firstname = "Unknown";
  var $lastname = "Unknown";
  var $username = "Guest";
  var $dbPasswordHash = NULL;
  var $isLoggedIn = FALSE;
  var $isAdmin = FALSE;

  function auth()
  {
    session_start();  
    if(isset($_SESSION['initiated']))
    {
        // Already logged in
        $this->securityuserid = $_SESSION['securityuserid'];
        $this->firstname = $_SESSION['firstname'];
        $this->lastname = $_SESSION['lastname'];
        $this->username = $_SESSION['username'];
        $this->isLoggedIn = TRUE;
        $this->isAdmin = $this->isAdmin();
    }
    else
    {
        // Need to log in 
        $this->isLoggedIn = FALSE;
    }
  }

  function loginWithNonce($theUsername, $theClientHash, $theClientNonce)
  {
    global $dbconn;
    
    $sql = "select * from tblsecurityuser where username='$theUsername'";
 
    $dbconnstatus = pg_connection_status($dbconn);
    if ($dbconnstatus ===PGSQL_CONNECTION_OK)
    {
        $result = pg_query($dbconn, $sql);
        while ($row = pg_fetch_array($result))
        {
            $dbPasswordHash = $row['password'];
            $this->username = $row['username'];
        }
    }

    // Authenticate against the database
    if ($this->checkClientHash($dbPasswordHash, $theClientNonce, $theClientHash))
    {
        // Login passed
        $result = pg_query($dbconn, $sql);
        while ($row = pg_fetch_array($result))
        {
            session_start();
            $_SESSION['initiated'] = TRUE;
            $_SESSION["securityuserid"] = $row['securityuserid'];
            $_SESSION['firstname'] = $row['firstname'];
            $_SESSION['lastname'] = $row['lastname'];
            $_SESSION['username'] = $row['username'];
            $this->isLoggedIn = TRUE;
            $this->logRequest("loginSecure");
            $this->isAdmin = $this->isAdmin();
        }
    }
    else
    {
        // Login failed
        $this->isLoggedIn = FALSE;
        $this->logRequest("loginFailure");
    }

    return $this->isLoggedIn;
  }

  function login($theUsername, $thePassword)
  {
    global $dbconn;

    $sql = "select * from tblsecurityuser where username='$theUsername'";
    
    $dbconnstatus = pg_connection_status($dbconn);
    if ($dbconnstatus ===PGSQL_CONNECTION_OK)
    {
        $result = pg_query($dbconn, $sql);
        while ($row = pg_fetch_array($result))
        {
            $dbpassword = $row['password'];
        }
    }

    // Authenticate against the database
    if (hash('md5', $thePassword)==$dbpassword)
    {
        // Login passed
        $result = pg_query($dbconn, $sql);
        while ($row = pg_fetch_array($result))
        {
            session_start();
            $_SESSION['initiated'] = TRUE;
            $_SESSION["securityuserid"] = $row['securityuserid'];
            $_SESSION['firstname'] = $row['firstname'];
            $_SESSION['lastname'] = $row['lastname'];
            $_SESSION['username'] = $row['username'];
            $this->isLoggedIn = TRUE;
            $this->logRequest("loginPlain");
        }
    }
    else
    {
        // Login failed
        $this->isLoggedIn = FALSE;
        $this->logRequest("loginFailure");
    }

    return $this->isLoggedIn;
  }

  function logout()
  {
    $this->isLoggedIn = FALSE;
    $_SESSION = array();
    if(isset($_COOKIE[session_name()]))
    {
        setcookie(session_name(), '', time()-42000, '/');
    }
    $this->logRequest("logout");
    $session_destroy;
  }

  function logRequest($type)
  {
    global $dbconn;
    global $wsversion;

    if ($type=="loginPlain")
    {
        $request = "Logged in using plain text";
    }
    elseif ($type=="loginSecure")
    {
        $request = "Logged in with security";
    }
    elseif ($type=="logout")
    {
        $request = "Logged out";
    }
    elseif ($type=="loginFailure")
    {
        $request = "Failed login attempt for username: '".$_POST['username']."'";
    }
    else
    {
        $request = "Unknown authentication request";
    }

    if ($this->getID()==NULL) 
    {
        $sql = "insert into tblrequestlog (request, ipaddr, wsversion) values ('".addslashes($request)."', '".$_SERVER['REMOTE_ADDR']."', '$wsversion')";
    }
    else
    {
        $sql = "insert into tblrequestlog (securityuserid, request, ipaddr, wsversion) values ('".$this->getID()."', '".addslashes($request)."', '".$_SERVER['REMOTE_ADDR']."', '$wsversion')";
    }

    pg_send_query($dbconn, $sql);
    $result = pg_get_result($dbconn);
    if(pg_result_error_field($result, PGSQL_DIAG_SQLSTATE))
    {
        echo pg_result_error($result)."--- SQL was $sql";
    }


  }

  function isLoggedIn()
  {
    return $this->isLoggedIn;
  }

  function getFirstname()
  {
    return $this->firstname;
  }
  
  function getLastname()
  {
    return $this->lastname;
  }
  
  function getUsername()
  {
    return $this->username;
  }

  function getID()
  {
    return $this->securityuserid;
  }

  
  function sitePermission($theSiteID, $thePermissionType)
  {
      return $this->getPermission($thePermissionType, 'site', $theSiteID); 
  }

  function subSitePermission($theSubSiteID, $thePermissionType)
  {

        global $dbconn;
        $sql = "select siteid from tblsubsite where subsiteid=$theSubSiteID";
        $dbconnstatus = pg_connection_status($dbconn);
        if ($dbconnstatus ===PGSQL_CONNECTION_OK)
        {
            pg_send_query($dbconn, $sql);
            $result = pg_get_result($dbconn);
            if(pg_num_rows($result)==0)
            {
                // No records match the id specified
                return false;
            }
            else
            {
                $result = pg_query($dbconn, $sql);
                while ($row = pg_fetch_array($result))
                {
                    return $this->getPermission($thePermissionType, 'site', $row['siteid']);
                }
            }
        }
        return false;
  }
  
  function treePermission($theTreeID, $thePermissionType)
  {
      return $this->getPermission($thePermissionType, 'tree', $theTreeID); 
  }

  function specimenPermission($theSpecimenID, $thePermissionType)
  {
        global $dbconn;
        $sql = "select treeid from tblspecimen where specimenid=$theSpecimenID";
        $dbconnstatus = pg_connection_status($dbconn);
        if ($dbconnstatus ===PGSQL_CONNECTION_OK)
        {
            pg_send_query($dbconn, $sql);
            $result = pg_get_result($dbconn);
            if(pg_num_rows($result)==0)
            {
                // No records match the id specified
                return false;
            }
            else
            {
                $result = pg_query($dbconn, $sql);
                while ($row = pg_fetch_array($result))
                {
                    return $this->getPermission($thePermissionType, 'tree', $row['treeid']);
                }
            }
        }
        return false;
  }
  
  function radiusPermission($theRadiusID, $thePermissionType)
  {
        global $dbconn;
        $sql = "select tblradius.radiusid, tblspecimen.treeid from tblradius, tblspecimen where tblradius.radiusid=$theRadiusID and tblradius.specimenid=tblspecimen.specimenid";
        $dbconnstatus = pg_connection_status($dbconn);
        if ($dbconnstatus ===PGSQL_CONNECTION_OK)
        {
            pg_send_query($dbconn, $sql);
            $result = pg_get_result($dbconn);
            if(pg_num_rows($result)==0)
            {
                // No records match the id specified
                return false;
            }
            else
            {
                $result = pg_query($dbconn, $sql);
                while ($row = pg_fetch_array($result))
                {
                    return $this->getPermission($thePermissionType, 'tree', $row['treeid']);
                }
            }
        }
        return false;
  }
  
  function vmeasurementPermission($theVMeasurementID, $thePermissionType)
  {
      return $this->getPermission($thePermissionType, 'vmeasurement', $theVMeasurementID); 
  }
  
  function getPermission($thePermissionType, $theObjectType, $theObjectID)
  {
        // $theObjectType should be one of site,tree, vmeasurement, default

        global $dbconn;
        // Check user is logged in first
        if ($this->isLoggedIn)
        {
            if(!($this->isAdmin))
            {
                // Not an admin user so do perms lookup
                $sql = "select * from cpgdb.getuserpermissionset($this->securityuserid, $theObjectType, $theObjectID)";
                $dbconnstatus = pg_connection_status($dbconn);
                if ($dbconnstatus ===PGSQL_CONNECTION_OK)
                {
                    $result = pg_query($dbconn, $sql);
                    $row = pg_fetch_array($result);
                    
                    if($row['denied']===FALSE)
                    {
                        // Check whether 'denied' over rules.
                        return False;
                    }

                    switch ($thePermissionType)
                    {
                    case "create":
                        return (bool) $row['cancreate'];
                    case "read":
                        return (bool) $row['canread'];
                    case "update":
                        return (bool) $row['canupdate'];
                    case "delete":
                        return (bool) $row['candelete'];
                    default:
                        // Incorrect permission type specified returning false
                        return False;
                    }
                }
                else
                {
                    // DB connection failed returning false
                    return False;
                }
            }
            else
            {
                // User is admin so return true
                return True;
            }
        }
        else
        {
            // Not logged in so returning false
            return false;
        }
  }

  function isAdmin()
  {
        global $dbconn;
        $sql = "select * from isadmin(".$this->securityuserid.") where isadmin=true";
        $dbconnstatus = pg_connection_status($dbconn);
        if ($dbconnstatus ===PGSQL_CONNECTION_OK)
        {
            pg_send_query($dbconn, $sql);
            $result = pg_get_result($dbconn);
            if(pg_num_rows($result)==0)
            {
                // No records match so not in admin group
                return false;
            }
            else
            {
                // Result returned so must be in admin group
                return true;
            }
        }
        return false;
  }

  function setPassword($username, $password)
  {
    global $dbconn;

    $sql = "update tblsecurityuser set password='".hash('md5', $password)."' where username='$username'";
    $dbconnstatus = pg_connection_status($dbconn);
    if ($dbconnstatus ===PGSQL_CONNECTION_OK)
    {
        $result = pg_query($dbconn, $sql);
    }
  }

  function nonce($timeseed="current")
  {
    if ($timeseed=="current")
    {
        // hash of current date
       return hash('md5', date("l F j H:i T Y"));
    }
    else
    {
        // hash of  date 1 minute ago
       return hash('md5', date("l F j H:i T Y", time()-60));
    }
  }

  function checkClientHash($dbPasswordHash, $cnonce, $clientHash)
  {
    $serverHashCurrent = hash('md5', $this->username.":".$dbPasswordHash.":".$this->nonce("current").":".$cnonce);
    $serverHashLast = hash('md5', $this->username.":".$dbPasswordHash.":".$this->nonce("last").":".$cnonce);

    //echo $this->username.":".$dbPasswordHash.":".$this->nonce("current").":".$cnonce."<br/>";
    //echo $serverHashCurrent."<br/>";
    //echo $clientHash."<br/>";

    //echo $this->username.":".$dbPasswordHash.":".$this->nonce("last").":".$cnonce."<br/>";
    //echo $serverHashLast."<br/>";

    if (($serverHashCurrent==$clientHash) || ($serverHashLast==$clientHash))
    {
        return true;
    }
    else
    {
        return false;
    }

  }

// End of class
} 
?>

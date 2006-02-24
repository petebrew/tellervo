/**
 * 
 */
package corina.graph;

import javax.imageio.ImageIO;
import javax.swing.*;

import corina.core.App;
import corina.prefs.PrefsDialog;
import corina.ui.Builder;
import corina.util.Center;

import java.awt.FlowLayout;
import java.awt.Container;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import java.awt.event.*;

import java.awt.print.*;

import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Lucas
 *
 */
public class GraphPrintDialog extends JPanel {
	
	public final static int PRINT_PRINTER = 1;
	public final static int PRINT_PDF = 2;
	public final static int PRINT_PNG = 3;
	
	// this is what actually does the graphing for us...
	private GrapherPanel plot;
	private GraphInfo gInfo;
	
	public GraphPrintDialog(JFrame parent, List graphs, GrapherPanel plot, int printType) {
		final JDialog d;
		final PreviewPanel preview;
		final ParamsPanel params;
		final GraphPrintDialog me = this;
		
		gInfo = plot.getPrinterGraphInfo();
		
		d = new JDialog(parent, "Printing / Exporting options...", true);
		d.setContentPane(this);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		preview = new PreviewPanel(plot, gInfo);
		params = new ParamsPanel(gInfo, printType);
				
		JTabbedPane tabs = new JTabbedPane();
		tabs.addTab("Parameters", params);
		tabs.addTab("Colors, Names, and Widths", new ElemColorPanel(graphs, gInfo.isPrinting()));
		tabs.addTab("Preview", preview);
		add(tabs, BorderLayout.CENTER);
		
		tabs.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
	            JTabbedPane pane = (JTabbedPane)evt.getSource();
	            
	            if(pane.getSelectedIndex() == 2) {
	            	// preview pane selected now...
	            	preview.preparePreview(params.getDPI());
	            }
			}
		});
		
	    JPanel okButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	    String i18text = (printType == PRINT_PRINTER) ? "print" : "save_as";
	    String deftext = (printType == PRINT_PRINTER) ? "Print..." : "Save as...";
	    
	    String oktext = null;
	    try {
	    	oktext = corina.ui.I18n.getText(i18text);
	    } catch (Exception e) { }
	    if (oktext == null) oktext = deftext;
	    JButton okButton = new JButton(oktext);
	    okButtonContainer.add(okButton);
	    
	    final GraphInfo _info = gInfo;
	    final GrapherPanel _plotter = plot;
	    final int _printType = printType;
	    okButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  if(_printType == PRINT_PRINTER) {
	    		  if(new GraphPrinter(_info, _plotter, params).doPrint())	    	  	    	  
	    			  d.dispose();
	    	  }
	    	  else if(_printType == PRINT_PDF) {
	    		  if(me.printPDF(_info, _plotter))
	    			  d.dispose();
	    	  }
	    	  else if(_printType == PRINT_PNG) {
	    		  if(me.printPNG(_info, _plotter))
	    			  d.dispose();
	    	  }
	      }
	    });

	    String canceltext = corina.ui.I18n.getText("cancel");
	    if (canceltext == null) canceltext = "Cancel";
	    JButton cancelButton = new JButton(canceltext);
	    okButtonContainer.add(cancelButton);
	    cancelButton.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	    	  d.dispose();
	      }
	    });
	    	    
	    add(okButtonContainer, BorderLayout.SOUTH);	  
	    
	    d.setSize(400, 500);
	    //d.pack();
	    Center.center(d);
	    d.setVisible(true);	    
	}
	
	protected boolean printPDF(final GraphInfo pinfo, final GrapherPanel plotter) {
		final JFileChooser chooser = new JFileChooser();
		final Container me = getParent();
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith(".pdf");
			}

			public String getDescription() {
				return "PDF Document files (*.pdf)";
			}
		});
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		//chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return false;

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				ProgressMonitor pm = new ProgressMonitor(me, // parent
						"Exporting graph to PDF file...", // message
						"", // note
						0, 5); // round up to 45 MB

				pm.setMillisToDecideToPopup(0);
				pm.setMillisToPopup(0);

				pm.setProgress(1);
				pm.setNote("Creating PDF document...");

				String fn = chooser.getSelectedFile().getAbsolutePath();
				if (!fn.toLowerCase().endsWith(".pdf"))
					fn += ".pdf";

				// create a PDF document
				
				Rectangle rect = new Rectangle(0, 0,
						(int) (pinfo.getDrawRange().span() * pinfo.getYearSize()),
						(int) (pinfo.getPrintHeight()));
				com.lowagie.text.Rectangle pageSize = new com.lowagie.text.Rectangle(
						rect.width, rect.height);
				Document document = new Document(pageSize);

				try {
					// create an associated writer
					PdfWriter writer = PdfWriter.getInstance(document,
							new FileOutputStream(new File(fn)));

					// "open" the PDF
					document.open();

					// set up a font mapper
					//DefaultFontMapper mapper = new DefaultFontMapper();					
					//FontFactory.registerDirectories();

					pm.setProgress(2);
					pm.setNote("Creating PDF memory context...");

					// do some magic to set up the pdf context
					PdfContentByte cb = writer.getDirectContent();
					Graphics2D g = cb.createGraphicsShapes(pageSize
							.width(), pageSize.height());

					plotter.computeRange(pinfo, g);
					StandardPlot agent = new StandardPlot(pinfo.getDrawRange(), pinfo);
					plotter.paintGraph(g, pinfo, agent);
					
					// finish up the PDF cruft
					//cb.addTemplate(tp, 0, 0);								

					//dispose of the graphics content
					pm.setProgress(4);
					pm.setNote("Cleaning up...");
					g.dispose();
				} catch (DocumentException de) {
					System.err.println(de.getMessage());
					de.printStackTrace();
				} catch (IOException ioe) {
					System.err.println(ioe.getMessage());
					ioe.printStackTrace();
				}

				document.close();
				pm.setProgress(5);
			}
		});
		return true;
	}

	protected boolean printPNG(final GraphInfo pinfo, final GrapherPanel plotter) {
		final JFileChooser chooser = new JFileChooser();
		final Container me = getParent();
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.getName().endsWith(".png");
			}

			public String getDescription() {
				return "PNG Image files (*.png)";
			}
		});
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		//chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return false;

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				ProgressMonitor pm = new ProgressMonitor(me, // parent
						"Exporting graph to PNG file...", // message
						"", // note
						0, 5); // round up to 45 MB

				pm.setMillisToDecideToPopup(0);
				pm.setMillisToPopup(0);

				pm.setProgress(1);
				pm.setNote("Creating PNG document...");
				
				Rectangle rect = new Rectangle(0, 0,
						(int) (pinfo.getDrawRange().span() * pinfo.getYearSize()),
						(int) (pinfo.getPrintHeight()));
				
				BufferedImage fileImage = new BufferedImage(rect.width,
						rect.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = fileImage.createGraphics();

				pm.setProgress(2);
				pm.setNote("Drawing graph...");

				plotter.computeRange(pinfo, g);
				StandardPlot agent = new StandardPlot(pinfo.getDrawRange(), pinfo);
				plotter.paintGraph(g, pinfo, agent);
				
				pm.setProgress(3);
				pm.setNote("Encoding as PNG and saving file...");

				try {
					String fn = chooser.getSelectedFile()
							.getAbsolutePath();
					if (!fn.toLowerCase().endsWith(".png"))
						fn += ".png";
					ImageIO.write(fileImage, "png", new File(fn));
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				pm.setProgress(4);
				pm.setNote("Cleaning up...");
				
				// ack! don't forget to dispose of the graphics context!
				g.dispose();

				pm.setProgress(5);
			}
		});
		return true;
	}
	
	private class ParamsPanel extends JPanel implements DocumentListener {		
		JTextField pixelsperyear;
		JTextField dpi;
		JTextField yearsperinch;
		JTextField pixelheight; 
		JTextField inchheight;
		JTextField inchwidth;
		JRadioButton landscape;
		JRadioButton reverse_landscape;
		JLabel printWidthHeight;
				
		GraphInfo gInfo;
		
	    DecimalFormat dfmt = new DecimalFormat("0.00");
	    
	    public int getDPI() {
	    	return Integer.parseInt(dpi.getText());
	    }
	    
	    public int getPrintFormat() {
	    	if(landscape.isSelected())
	    		return PageFormat.LANDSCAPE;
	    	if(reverse_landscape.isSelected())
	    		return PageFormat.REVERSE_LANDSCAPE;
	    	
	    	// so it doesn't complain...
	    	return PageFormat.LANDSCAPE;
	    }
		
		public ParamsPanel(GraphInfo g, int printType) {
			super();
		
			gInfo = g;
			
			setLayout(new BorderLayout());
			JComponent co;
			JLabel jl;
			GridBagConstraints gbc;		    
		    
			co = new JPanel(new GridBagLayout());
			co.setToolTipText("<html>Sets parameters required for graph rendering");			
			co.setBorder(BorderFactory.createTitledBorder("Graph basic constraints"));
			
			gbc = new GridBagConstraints();
		    gbc.anchor = GridBagConstraints.WEST;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(2, 2, 2, 2);
		    gbc.gridy = 0;
			
		    // start a row
		    gbc.gridx = 0;
		    
		    jl = new JLabel("<html>Pixel width of a year and<br>Pixel height of 10 units");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    pixelsperyear = new JTextField(6);
		    pixelsperyear.setText(Integer.toString(10));
		    pixelsperyear.getDocument().addDocumentListener(this);
		    pixelsperyear.getDocument().putProperty("propname", "pixelsperyear");
		    jl.setLabelFor(pixelsperyear);
		    co.add(pixelsperyear, gbc);

		    // start a row
		    gbc.gridy++;
		    gbc.gridx = 0;
		    
		    jl = new JLabel("Pixels per inch (DPI)");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    dpi = new JTextField(6);
		    if(printType != GraphPrintDialog.PRINT_PRINTER)
		    	dpi.setEditable(false);
		    dpi.setText(Integer.toString(100));
		    dpi.getDocument().addDocumentListener(this);
		    dpi.getDocument().putProperty("propname", "dpi");
		    jl.setLabelFor(dpi);
		    co.add(dpi, gbc);

		    // start a row
		    gbc.gridy++;
		    gbc.gridx = 0;
		    
		    jl = new JLabel("<html>Years per inch and<br>10 Unit count per inch");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    yearsperinch = new JTextField(6);
		    yearsperinch.setEditable(false);
		    float ypi = Float.parseFloat(dpi.getText()) / Float.parseFloat(pixelsperyear.getText());
		    yearsperinch.setText(dfmt.format(ypi));
		    yearsperinch.getDocument().addDocumentListener(this);
		    yearsperinch.getDocument().putProperty("propname", "yearsperinch");
		    jl.setLabelFor(yearsperinch);
		    co.add(yearsperinch, gbc);
		    
		    add(co, BorderLayout.NORTH);
		    
			co = new JPanel(new GridBagLayout());
			co.setToolTipText("<html>View parameters for graph output");			
			co.setBorder(BorderFactory.createTitledBorder("Graph size"));
			
			gbc = new GridBagConstraints();
		    gbc.anchor = GridBagConstraints.WEST;
		    gbc.fill = GridBagConstraints.NONE;
		    gbc.insets = new Insets(2, 2, 2, 2);
		    gbc.gridy = 0;
			
		    // start a row
		    gbc.gridx = 0;
		    
		    jl = new JLabel("<html>Graph height, in pixels");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    pixelheight = new JTextField(6);
		    pixelheight.setText(Integer.toString(gInfo.getPrintHeight()));
		    pixelheight.getDocument().addDocumentListener(this);
		    pixelheight.getDocument().putProperty("propname", "pixelheight");
		    jl.setLabelFor(pixelheight);
		    co.add(pixelheight, gbc);

		    // start a row
		    gbc.gridy++;
		    gbc.gridx = 0;
		    
		    jl = new JLabel("<html>Graph height, in inches");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    inchheight = new JTextField(6);
		    float ih = 0;
		    try {
		    	ih = (float) gInfo.getPrintHeight() / Float.parseFloat(dpi.getText());
		    } catch (Exception e) { }
		    inchheight.setText(dfmt.format(ih));
		    inchheight.getDocument().addDocumentListener(this);
		    inchheight.getDocument().putProperty("propname", "inchheight");
		    jl.setLabelFor(inchheight);
		    co.add(inchheight, gbc);

		    // start a row
		    gbc.gridy++;
		    gbc.gridx = 0;
		    
		    jl = new JLabel("<html>Graph width, in inches");
		    co.add(jl, gbc);
		    gbc.gridx++;
		    
		    inchwidth = new JTextField(6);
		    inchwidth.setEditable(false);
		    float iw = 0;
		    try {
		    	iw = ((float) gInfo.getDrawRange().span() * gInfo.getYearSize()) / 
		    	Float.parseFloat(dpi.getText());
		    } catch (Exception e) { }
		    inchwidth.setText(dfmt.format(iw));
		    inchwidth.getDocument().addDocumentListener(this);
		    inchwidth.getDocument().putProperty("propname", "inchwidth");
		    jl.setLabelFor(inchwidth);
		    co.add(inchwidth, gbc);
		    
		    add(co, BorderLayout.CENTER);		    
		    
		    if (printType == GraphPrintDialog.PRINT_PRINTER) {
				co = new JPanel(new GridBagLayout());
				co.setToolTipText("<html>Set printing preferences");
				co.setBorder(BorderFactory
						.createTitledBorder("Printing preferences"));

				gbc = new GridBagConstraints();
				gbc.anchor = GridBagConstraints.WEST;
				gbc.fill = GridBagConstraints.NONE;
				gbc.insets = new Insets(2, 2, 2, 2);
				gbc.gridy = 0;
				
				landscape = new JRadioButton("Print landscape", true);
				reverse_landscape = new JRadioButton("Print reverse landscape");
				
				ButtonGroup printbuttons = new ButtonGroup();
				
				printbuttons.add(landscape);
				printbuttons.add(reverse_landscape);

				// start a row
				gbc.gridx = 0;				
				co.add(landscape, gbc);
				gbc.gridx++;
				co.add(reverse_landscape, gbc);

				gbc.gridy++;
				gbc.gridx = 0;
				gbc.fill = gbc.HORIZONTAL;
				gbc.gridwidth = gbc.REMAINDER;
				
				co.add(new JLabel("<html>When specifying paper size during printing, " +
						"make sure to use <i>at least</i> the following paper width and height:<br>"),
						gbc);
				
				gbc.gridy++;
				gbc.gridx = 0;
				gbc.gridwidth = gbc.REMAINDER;
				
				printWidthHeight = new JLabel("n/a");
				co.add(printWidthHeight, gbc);

				add(co, BorderLayout.SOUTH);
			}		   
		    
		    updatePrintDimensions();
		}
		
		private void updatePrintDimensions() {
		    float pgraphHeight = 0.0f;
		    try {
		    	pgraphHeight = ((float) gInfo.getDrawRange().span() * gInfo.getYearSize()) / 
		    		Float.parseFloat(dpi.getText());
		    } catch (Exception e) { }
		    
		    float pgraphWidth = 0.0f;
			try {
		    	pgraphWidth = (float) gInfo.getPrintHeight() / Float.parseFloat(dpi.getText());
			} catch (Exception e) {	}		 
			
			// half an inch on each side!
			pgraphWidth += 1f;
			pgraphHeight += 1f;
		    
			printWidthHeight.setText("<html><b>" +
					dfmt.format(pgraphWidth) + "\"</b>(w) x <b>" +
					dfmt.format(pgraphHeight) + "\"</b>(h)");			
		}
		
		private void recalc(DocumentEvent d) {
			javax.swing.text.Document doc = (javax.swing.text.Document) d.getDocument();
			String source = (String) doc.getProperty("propname");
			
			if(source.equals("pixelsperyear")) {
				try {
					float fdpi = Float.parseFloat(dpi.getText());
					float fppy = Float.parseFloat(pixelsperyear.getText());
					yearsperinch.setText(dfmt.format(fdpi / fppy));
					gInfo.setYearSize((int) fppy);
					
				    float iw = 0;
				    try {
				    	iw = ((float) gInfo.getDrawRange().span() * gInfo.getYearSize()) / 
				    	Float.parseFloat(dpi.getText());
				    } catch (Exception e) { }
				    inchwidth.setText(dfmt.format(iw));
					
				} catch (Exception e) {					
				}
			}
			else if(source.equals("dpi")) {
				try {
					float fdpi = Float.parseFloat(dpi.getText());
					float fppy = Float.parseFloat(pixelsperyear.getText());
					yearsperinch.setText(dfmt.format(fdpi / fppy));
					
			    	float ih = (float) gInfo.getPrintHeight() / Float.parseFloat(dpi.getText());
					inchheight.setText(dfmt.format(ih));
					
				    float iw = 0;
				    try {
				    	iw = ((float) gInfo.getDrawRange().span() * gInfo.getYearSize()) / 
				    	Float.parseFloat(dpi.getText());
				    } catch (Exception e) { }
				    inchwidth.setText(dfmt.format(iw));
					
				} catch (Exception e) {					
				}
			}
			else if(source.equals("pixelheight")) {
				try {
					gInfo.setPrintHeight(Integer.parseInt(pixelheight.getText()));
			    	float ih = (float) gInfo.getPrintHeight() / Float.parseFloat(dpi.getText());
					inchheight.setText(dfmt.format(ih));
				} catch (Exception e) {					
				}
			}
			else if(source.equals("inchheight")) {
				try {
			    	int npix = (int) (Float.parseFloat(inchheight.getText()) * 
			    					  Float.parseFloat(dpi.getText()));
			    	gInfo.setPrintHeight(npix);
					pixelheight.setText(Integer.toString(npix));
				} catch (Exception e) {					
				}
			}
			/*
			else if(source.equals("dpi")) {
			    yearsperinch.setValue(new Float(
			    		((Integer)dpi.getValue()).floatValue() /
			    		((Integer)pixelsperyear.getValue()).floatValue() 
			    		));								
			}
			*/
			updatePrintDimensions();
		}
		
		public void insertUpdate(DocumentEvent d) {
			recalc(d);
		}
		public void removeUpdate(DocumentEvent d) {
			recalc(d);
		}
		public void changedUpdate(DocumentEvent d) {
			recalc(d);
		}
	}
	
	private class PreviewPanel extends JPanel {
	
		private PreviewInsidePane inpane;
		private JScrollPane scrollerpane;
		
		private class PreviewInsidePane extends JPanel {
			private GrapherPanel plotter;
			private GraphInfo pinfo;
			private StandardPlot agent;
			private double scale;
			private double zoom;
			
			private double fullscale;
			
			public PreviewInsidePane(GrapherPanel p, GraphInfo g) {
				super();
				
				setBackground(g.getBackgroundColor());
				
				plotter = p;
				pinfo = g;
				g.setPrintHeight(plotter.getHeight());
				zoom = 1.0;
			}

			public void preparePreview() {
				plotter.computeRange(pinfo, null);
				
				fullscale = scale * zoom;
				
				setPreferredSize(new Dimension(
						(int) (pinfo.getDrawRange().span() * pinfo.getYearSize() * fullscale), 
						(int) (pinfo.getPrintHeight() * fullscale)));
				agent = new StandardPlot(pinfo.getDrawRange(), pinfo);
				revalidate();
				repaint();				
			}
			
			public void setZoom(float zoom) {
				this.zoom = zoom;
			}
			
			public void preparePreview(int scale) {
				this.scale = 72.0 / (float) scale;
				
				preparePreview();
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				((Graphics2D)g).scale(fullscale, fullscale);
				plotter.paintGraph(g, pinfo, agent);
			}				
		}
		
		public PreviewPanel(GrapherPanel p, GraphInfo g) {
			super(new BorderLayout());

			inpane = new PreviewInsidePane(p, g);
			scrollerpane = new JScrollPane(inpane);
			add(scrollerpane, BorderLayout.CENTER);
			
			JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		    final JSlider slider = new JSlider(10, 400, 100);
		    final JLabel zoom = new JLabel("Zoom: 100%");
		    
		    final PreviewPanel _this = this;
			slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					zoom.setText("Zoom: " + slider.getValue() + "%");
					_this.inpane.setZoom(slider.getValue() / 100.0f);
					_this.inpane.preparePreview();
				 }
			});
			    
			JLabel large = new JLabel(Builder.getIcon("mountains-large.png"));
			JLabel small = new JLabel(Builder.getIcon("mountains-small.png"));

			// allow clicking on these!
			large.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					slider.setValue(slider.getValue() + 5);
				}
			});
			small.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					slider.setValue(slider.getValue() - 5);
				}
			});

			bottom.add(zoom);
			bottom.add(small);
			bottom.add(slider);
			bottom.add(large);
			
			add(bottom, BorderLayout.SOUTH);
			
			preparePreview(72);
		}
		
		public void preparePreview(int scale) {
			inpane.preparePreview(scale);
			
			// TODO: Move the scrollpane to the lower left corner
		}
	}
	
	private class ElemColorPanel extends JPanel {
		public ElemColorTable table;
		
		public ElemColorPanel(List graphs, boolean isPrinting) {
			super();
			
			table = new ElemColorTable(graphs, isPrinting);
			add(table);
		}
	}
	
	
	private class GraphPrinter implements Pageable, Printable {
		private PrinterJob job;
		private PageFormat format;
		private GraphInfo info;
		
		private GrapherPanel plotter;
		
		private double pscale;
		
		public GraphPrinter(GraphInfo info, GrapherPanel plotter, ParamsPanel params) {
		    job = PrinterJob.getPrinterJob();
		    Paper paper = new Paper();
		    PageFormat pfmt = new PageFormat();
		    double h, w;
		    
		    this.info = info;
		    this.plotter = plotter; 
		    
		    // set the orientation
		    pfmt.setOrientation(params.getPrintFormat());

		    // CONFUSED?
		    // we print landscape!
		    // so, confusingly, the "width" of the printed graph is the height 
		    // of the graph we see on the screen and the "height" of the printed 
		    // graph is the width we see on the screen
		    
		    // we need to make a scale from 72nds of an inch to "DPI" of an inch...
		    h = gInfo.getDrawRange().span() * gInfo.getYearSize();
		    w = gInfo.getPrintHeight();
		    pscale = 72.0 / (double) params.getDPI();
		    
		    // width, height is now in pixels.. 
		    // convert it to inches and then multiply by 72
		    h *= pscale;
		    w *= pscale;
		    
		    int m1 = 72;
		    int m2 = m1 * 2;
		    
		    paper.setSize(w + m2, h + m2);
		    paper.setImageableArea(m1, m1, w, h);
		    pfmt.setPaper(paper);

		    /*

		    // for some reason, this is retarded...
		    if(h > w) {
		    	paper.setSize(w + m2, h + m2);
		    	paper.setImageableArea(m1, m1, w + m1, h + m1);
		    }
		    else {
		    	paper.setSize(h + m2, w + m2);
		    	paper.setImageableArea(m1, m1, h + m1, w + m1);		    	
		    }
//		    if(params.getPrintFormat() == PageFormat.PORTRAIT)
//		    	paper.setImageableArea(18, 18, w + 18, h + 18);
//		    else
//	    		paper.setImageableArea(18, 18, h + 18, w + 18);
		    
			pfmt.setPaper(paper);		    		    	
		    pfmt.setOrientation(params.getPrintFormat());
		    */
		    format = pfmt;

		    job.setJobName("Corina plot");
		    job.setPageable(this); 
		}
		
		public boolean doPrint() {
			if(job.printDialog()) {
				try {
					job.print();
				} catch (PrinterException pe) {
					return false;
				}
				return true;
			}
			else
				return false;
		}
		
		public int print(Graphics g, PageFormat format, int pagenum) {
			if(pagenum != 0)
				return NO_SUCH_PAGE;
			
			// move out of our margins...
			((Graphics2D) g).translate(format.getImageableX(), 
					format.getImageableY());
			((Graphics2D) g).scale(pscale, pscale);			
			
			plotter.computeRange(info, g);
			StandardPlot agent = new StandardPlot(info.getDrawRange(), info);
			plotter.paintGraph(g, info, agent);
			
			return PAGE_EXISTS;
		}
		
		public int getNumberOfPages() {
			return 1;
		}
		
		public PageFormat getPageFormat(int pageIndex) {
			if(pageIndex != 0)
				return null;
			
			return format;
		}
		
		public Printable getPrintable(int pageIndex) {
			if(pageIndex != 0)
				return null;
			
			return this;
		}
	}
}
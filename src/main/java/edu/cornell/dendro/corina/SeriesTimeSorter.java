package edu.cornell.dendro.corina;

import java.util.Comparator;

import org.tridas.interfaces.ITridasSeries;

public class SeriesTimeSorter implements Comparator<ITridasSeries>{

	public Boolean undatedFirst = true;

	@Override
	public int compare(ITridasSeries ser1, ITridasSeries ser2) {
				
		Integer ser1firstyear = null;
		Integer ser1lastyear = null;
		Integer ser2firstyear = null;
		Integer ser2lastyear = null;
		
		
		if(ser1.isSetInterpretation())
		{
			if(ser1.getInterpretation().isSetFirstYear())
			{
				ser1firstyear = ser1.getInterpretation().getFirstYear().getValue();
			}
			if(ser1.getInterpretation().isSetLastYear())
			{
				ser1lastyear = ser1.getInterpretation().getLastYear().getValue();
			}
		}
		
		if(ser2.isSetInterpretation())
		{
			if(ser2.getInterpretation().isSetFirstYear())
			{
				ser2firstyear = ser2.getInterpretation().getFirstYear().getValue();
			}
			if(ser2.getInterpretation().isSetLastYear())
			{
				ser2lastyear = ser2.getInterpretation().getLastYear().getValue();
			}
		}
		
		if(ser1firstyear==null || ser1lastyear==null)
		{
			if (undatedFirst==true) return 1;
			else return 0;
		}
		
		if(ser2firstyear==null || ser2lastyear==null)
		{
			if (undatedFirst==true) return 0;
			else return 1;
		}
		
		if(ser1lastyear!=ser2lastyear)
		{
			return ser2lastyear.compareTo(ser1lastyear);
		}
		else
		{
			return ser2firstyear.compareTo(ser1firstyear);
		}
		
		
	}
}


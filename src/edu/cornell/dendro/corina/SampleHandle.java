package edu.cornell.dendro.corina;

import java.io.IOException;

public class SampleHandle {
	private ObsFileElement element;
	private Sample sample;
	private BaseSample baseSample;
	private boolean loaded;
	
	public SampleHandle(ObsFileElement element) {
		this.element = element;
		this.baseSample = this.sample = null;

		loaded = false;
		lastModified = -1;
	}
	
	public ObsFileElement getElement() {
		return element;
	}
	
	public Sample getSample() throws IOException {
		if(sample == null)
			sample = element.load();
		
		loaded = true;
		return sample;
	}
	
	public BaseSample getBaseSample() throws IOException {
		if(sample != null)
			baseSample = new BaseSample(sample);
		else
			baseSample = element.loadBasic();

		loaded = true;
		return baseSample;
	}
	
	public void reset() {
		loaded = false;
		baseSample = sample = null;
	}
	
	public void setBaseSample(BaseSample bs) {
		if(loaded)
			throw new java.lang.UnsupportedOperationException("Can't set base sample on already loaded sample!");
		
		baseSample = new BaseSample(bs);
		loaded = true;
	}
	
	private long lastModified;
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
}

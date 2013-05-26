package com.zb.jnlxc.model;

import java.util.Set;

public class FlowStateImage {
	private Set<ImgLocate> imageLocates;
	private String url;
	public Set<ImgLocate> getImageLocates() {
		return imageLocates;
	}
	public void setImageLocates(Set<ImgLocate> imageLocates) {
		this.imageLocates = imageLocates;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

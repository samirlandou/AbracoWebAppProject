package br.com.abracowebmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TB_IMAGE")
public class ImageDomain extends GenericDomain{
	
	@Column(name = "DE_PATH", length = 100, nullable = false)
	private String path;
	
	@Column(name = "NM_IMAGE", length = 20, nullable = false)
	private String imageName;
	
	@Column(name = "FG_RANDOM_IMAGE", nullable = false)
	private Boolean randomImageFlag;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Boolean getRandomImageFlag() {
		return randomImageFlag;
	}

	public void setRandomImageFlag(Boolean randomImageFlag) {
		this.randomImageFlag = randomImageFlag;
	}
}

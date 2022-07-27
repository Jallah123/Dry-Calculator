package com.dryness;

import java.awt.Image;

public class DrynessResult
{
	private final String text;
	private final Image image;

	public DrynessResult(String text, Image image)
	{
		this.text = text;
		this.image = image;
	}

	public String getText()
	{
		return text;
	}

	public Image getImage()
	{
		return image;
	}
}

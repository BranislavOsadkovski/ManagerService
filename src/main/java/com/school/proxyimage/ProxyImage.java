/**
 * Copyright the original author or authors.
 */
package com.school.proxyimage;

import com.school.objects.Image;
import com.school.util.StudentJDBCTemplate;

/**
 * @author Branislav
 *
 */
public class ProxyImage {
	private static int imgId = 0;
	private static Image image;
	private static RImage rimage;

	public ProxyImage() {

	}
 

	public static Image getProxyImage(int id,StudentJDBCTemplate template) {
		 
		 
		if (imgId != id) {
			imgId= id;
			 rimage = new RImage(imgId, template);
			 image = rimage.getRealImage();
			return image;
		} else { 
			return  image;
		}
	}

}

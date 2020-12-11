/**
 * Copyright the original author or authors.
 */
package com.school.proxyimage;

import com.school.objects.ImageImpl;
import com.school.util.StudentJDBCTemplate;

/**
 * @author Branislav
 *
 */
/**
 * @author Branislav
 *
 */
public class ProxyImage {
	private static int imgId = 0;
	private static ImageImpl image;
	private static RImage rimage;

	public ProxyImage() {

	}

	/**
	 * returns cached image object if same @param id passed to the method or fetches
	 * a new Image from database if different @param id value is given
	 * 
	 * @param id
	 * @param template must not be null
	 * @return image
	 */
	public static ImageImpl getProxyImage(int id, StudentJDBCTemplate template) {

		if (imgId != id) {
			imgId = id;
			rimage = new RImage(imgId, template);
			image = rimage.getRealImage();
			return image;
		} else {
			return image;
		}
	}

}

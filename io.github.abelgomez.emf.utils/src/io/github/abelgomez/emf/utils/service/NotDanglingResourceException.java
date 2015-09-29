package io.github.abelgomez.emf.utils.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;

public class NotDanglingResourceException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotDanglingResourceException(URI resourceUri) {
		super(NLS.bind("Resource {0} exists", resourceUri.toString()));
	}
}

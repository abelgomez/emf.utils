package io.github.abelgomez.emf.utils.service;

import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;

public class UnknownEPackageException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnknownEPackageException(URI nsUri) {
		super(NLS.bind("Unknown EPackage {0}", nsUri.toString()));
	}

}

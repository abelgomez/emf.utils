package io.github.abelgomez.emf.utils.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class ExceptionStatus extends Status {

	ExceptionStatus(String message, Exception e) {
		super(ERROR, EMFUtilsUiPlugin.PLUGIN_ID, message, e);
	}
	
	public static IStatus createExceptionStatus(Exception e) {
		return new ExceptionStatus(e.getLocalizedMessage(), e);
	}

	public static IStatus createExceptionStatus(String message, ClassCastException e) {
		return new ExceptionStatus(message, e);
	}
	
}

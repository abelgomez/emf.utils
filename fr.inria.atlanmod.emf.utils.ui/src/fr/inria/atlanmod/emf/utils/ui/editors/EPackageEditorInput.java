/*
 * Created on 11-feb-2005
 *
 */
package fr.inria.atlanmod.emf.utils.ui.editors;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class EPackageEditorInput implements IEditorInput {
	
	private EPackage input;
	
	public EPackageEditorInput(EPackage input) {
		this.input = input;
	}
	public boolean exists() {
		return true;
	}
	public ImageDescriptor getImageDescriptor() {
		return null;
	}
	public String getName() {
		return input.getNsPrefix();
	}

	public IPersistableElement getPersistable() {
		return null;
	}
	public String getToolTipText() {
		return input.getName() + " EPackage - " + input.getNsURI();
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if(adapter == EPackage.class)
			return input;
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	public EPackage getPackage() {
		return input;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof EPackageEditorInput))
            return false;
        EPackageEditorInput objEPackageEditorInput = (EPackageEditorInput) obj;
        if (objEPackageEditorInput.getPackage() == null)
        	return false;
        return objEPackageEditorInput.getPackage().equals(input);
	}


}
 
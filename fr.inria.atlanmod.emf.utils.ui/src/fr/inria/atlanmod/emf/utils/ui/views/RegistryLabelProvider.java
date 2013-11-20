package fr.inria.atlanmod.emf.utils.ui.views;

import java.util.Map.Entry;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

class RegistryLabelProvider extends StyledCellLabelProvider implements ILabelProvider {
	
	@Override
	public void update(ViewerCell cell) {
		Object element = cell.getElement();
		StyledString text = new StyledString();
		if (element instanceof Entry<?, ?>) {
			String nsURI = (String) ((Entry<?, ?>) element).getKey();
			Resource eResource = EPackage.Registry.INSTANCE.getEPackage(nsURI).eResource();
			text.append(nsURI);
			text.append(" (Resource: ", StyledString.DECORATIONS_STYLER);
			if (eResource != null) {
				text.append(eResource.getURI().toString(), StyledString.DECORATIONS_STYLER);
			} else {
				text.append("<unknown>", StyledString.DECORATIONS_STYLER);
			}
			text.append(")", StyledString.DECORATIONS_STYLER);

		} else {
			text.append(getText(element));
		}
		cell.setText(text.toString());
		cell.setStyleRanges(text.getStyleRanges());
		cell.setImage(getImage(element));
		super.update(cell);
	}

	
	@Override
	public String getText(Object element) {
		return element == null ? "" : element.toString();
	}
	
	@Override
	public Image getImage(Object element) {
		return ExtendedImageRegistry.getInstance().getImage(EcoreEditPlugin.INSTANCE.getImage("full/obj16/EPackage"));
	}
}
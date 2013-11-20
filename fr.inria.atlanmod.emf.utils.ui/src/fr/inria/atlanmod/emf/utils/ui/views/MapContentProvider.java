package fr.inria.atlanmod.emf.utils.ui.views;

import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class MapContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Map<?, ?> map = (Map<?, ?>) inputElement;
		return map.entrySet().toArray();
	}

}
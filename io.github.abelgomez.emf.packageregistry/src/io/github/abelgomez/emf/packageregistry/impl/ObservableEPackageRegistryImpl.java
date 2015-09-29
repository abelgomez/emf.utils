package io.github.abelgomez.emf.packageregistry.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

public class ObservableEPackageRegistryImpl extends Observable implements EPackage.Registry {

	private EPackageRegistryImpl map;

	/**
	 * Creates a non-delegating instance.
	 */
	public ObservableEPackageRegistryImpl() {
		map = new EPackageRegistryImpl();
	}
	
	/**
	 * Creates a delegating instance.
	 */
	public ObservableEPackageRegistryImpl(EPackage.Registry delegateRegistry) {
		map = new EPackageRegistryImpl(delegateRegistry);
	}
	
	@Override
	public synchronized int size() {
		return map.size();
	}

	@Override
	public synchronized boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * Returns whether this map or the delegate map contains this key. Note that
	 * if there is a delegate map, the result of this method may
	 * <em><b>not</b></em> be the same as <code>keySet().contains(key)</code>.
	 * 
	 * @param key
	 *            the key whose presence in this map is to be tested.
	 * @return whether this map or the delegate map contains this key.
	 */
	@Override
	public synchronized boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public synchronized boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public synchronized Object get(Object key) {
		return map.get(key);
	}

	@Override
	public synchronized Object put(String key, Object value) {
		Object returnValue = map.put(key, value);
		setChanged();
		notifyObservers();
		return returnValue;
	}

	@Override
	public synchronized Object remove(Object key) {
		Object returnValue = map.remove(key);
		setChanged();
		notifyObservers();
		return returnValue;
	}

	@Override
	public synchronized void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
		setChanged();
		notifyObservers();
	}

	@Override
	public synchronized void clear() {
		map.clear();
		setChanged();
		notifyObservers();
	}

	@Override
	public synchronized Set<String> keySet() {
		return map.keySet();
	}

	@Override
	public synchronized Collection<Object> values() {
		return map.values();
	}

	@Override
	public synchronized Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@Override
	public synchronized EPackage getEPackage(String nsURI) {
		return map.getEPackage(nsURI);
	}

	@Override
	public synchronized EFactory getEFactory(String nsURI) {
		return map.getEFactory(nsURI);
	}
}

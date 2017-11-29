package com.sendsafely.enums;

/**
 * Enum describing the current state of the package.
 *
 */
public enum PackageState {

	PACKAGE_STATE_DELETED_PARTIALLY_COMPLETE(-4), 
	PACKAGE_STATE_DELETED_INCOMPLETE(-3), 
	PACKAGE_STATE_TEMP(-2), 
	PACKAGE_STATE_DELETED_COMPLETE(-1), 
	PACKAGE_STATE_IN_PROGRESS(0), 
	PACKAGE_STATE_EXPIRED_INCOMPLETE(1),
	PACKAGE_STATE_EXPIRED_COMPLETE(2),
	PACKAGE_STATE_ACTIVE_COMPLETE(3),
	PACKAGE_STATE_ACTIVE_INCOMPLETE(4),
	PACKAGE_STATE_ACTIVE_PARTIALLY_COMPLETE(5),
	PACKAGE_STATE_EXPIRED_PARTIALLY_COMPLETE(6);
	
	private int id;
	
	private PackageState(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean compare(int i){return id == i;}
	
	public static PackageState GetValue(int _id)
    {
		PackageState[] states = PackageState.values();
        for(int i = 0; i < states.length; i++)
        {
            if(states[i].compare(_id))
                return states[i];
        }
        return null;
    }
	
}

package git.janek79.javaeese.eese;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import git.janek79.javaeese.eese.entity.User;

public class UsersListModel implements ListModel<User> {

	private List<User> list;
	
	public UsersListModel(List<User> list) {
		this.list = list;
	}
	
	/**
	 * Not implemented
	 */
	@Override
	@Deprecated
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getElementAt(int index) {
		return list.get(index);
	}

	@Override
	public int getSize() {
		return list.size();
	}

	/**
	 * Not implemented
	 */
	@Override
	@Deprecated
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}
	

}

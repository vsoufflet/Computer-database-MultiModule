package com.excilys.computerdatabase.wrapper;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;

public class ListWrapper {

	List<Computer> computerList;
	int numberofComputers;

	public List<Computer> getComputerList() {
		return computerList;
	}

	public void setComputerList(List<Computer> computerList) {
		this.computerList = computerList;
	}

	public int getNumberofComputers() {
		return numberofComputers;
	}

	public void setNumberofComputers(int numberofComputers) {
		this.numberofComputers = numberofComputers;
	}

	@Override
	public String toString() {
		return "ListWrapper [computerList=" + computerList
				+ ", numberofComputers=" + numberofComputers + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerList == null) ? 0 : computerList.hashCode());
		result = prime * result + numberofComputers;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListWrapper other = (ListWrapper) obj;
		if (computerList == null) {
			if (other.computerList != null)
				return false;
		} else if (!computerList.equals(other.computerList))
			return false;
		if (numberofComputers != other.numberofComputers)
			return false;
		return true;
	}

}

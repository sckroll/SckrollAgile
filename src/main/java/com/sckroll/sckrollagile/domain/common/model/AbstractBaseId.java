package com.sckroll.sckrollagile.domain.common.model;

import java.io.Serializable;

public abstract class AbstractBaseId implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	public AbstractBaseId(long id) {
		this.id = id;
	}

	public long value() {
		return id;
	}

	public boolean isValid() {
		return id > 0;
	}
}

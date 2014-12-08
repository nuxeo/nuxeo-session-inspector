/*
 * (C) Copyright 2014 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     <a href="mailto:grenard@nuxeo.com">Guillaume</a>
 */
package org.nuxeo.ecm.platform.sessioninspector.jsf.model;

/**
 * Might be useful to check for references stats (in case same object is referenced in several items state).
 *
 * @since 5.9.2
 */
public class ObjectStatistics {

    private String type;

    private long nbInstance;

    private long cumulatedSize;

    /**
     * @param type
     * @param nbObject
     * @param cumulatedSize
     */
    public ObjectStatistics(String type, long nbInstance, long cumulatedSize) {
        super();
        this.type = type;
        this.nbInstance = nbInstance;
        this.cumulatedSize = cumulatedSize;
    }

    /**
     * @param type
     * @param nbObject
     * @param cumulatedSize
     */
    public ObjectStatistics(String type) {
        this(type, 1, 0);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getNbInstance() {
        return nbInstance;
    }

    public void setNbInstance(long nbInstance) {
        this.nbInstance = nbInstance;
    }

    public long getCumulatedSize() {
        return cumulatedSize;
    }

    public void setCumulatedSize(long cumulatedSize) {
        this.cumulatedSize = cumulatedSize;
    }

}

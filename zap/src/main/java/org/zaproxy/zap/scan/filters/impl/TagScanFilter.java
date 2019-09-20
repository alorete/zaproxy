/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2019 The ZAP Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaproxy.zap.scan.filters.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.parosproxy.paros.model.HistoryReference;
import org.zaproxy.zap.model.StructuralNode;
import org.zaproxy.zap.scan.filters.FilterCriteria;
import org.zaproxy.zap.scan.filters.ScanFilter;

/** @author KSASAN preetkaran20@gmail.com */
public class TagScanFilter implements ScanFilter {

    private Set<String> tags = new LinkedHashSet<>();

    private FilterCriteria filterCriteria;

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public FilterCriteria getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(FilterCriteria filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    @Override
    public boolean isFiltered(StructuralNode node) {
        HistoryReference href = node.getHistoryReference();
        if (href != null) {
            List<String> nodeTags = href.getTags();
            switch (filterCriteria) {
                case INCLUDE_ALL:
                    return nodeTags.containsAll(this.tags);
                case EXCLUDE:
                    for (String tag : nodeTags) {
                        if (!this.tags.contains(tag)) {
                            return false;
                        }
                    }
                    return true;
                case INCLUDE:
                    for (String tag : nodeTags) {
                        if (this.tags.contains(tag)) {
                            return true;
                        }
                    }
                    return false;
                default:
                    return true;
            }
        } else {
            return true;
        }
    }
}

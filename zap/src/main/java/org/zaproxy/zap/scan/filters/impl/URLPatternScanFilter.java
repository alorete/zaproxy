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
import java.util.Set;
import java.util.regex.Pattern;
import org.parosproxy.paros.model.HistoryReference;
import org.zaproxy.zap.model.StructuralNode;
import org.zaproxy.zap.scan.filters.ScanFilter;
import org.zaproxy.zap.scan.filters.UrlPatternFilterBean;

/** @author KSASAN preetkaran20@gmail.com */
public class URLPatternScanFilter implements ScanFilter {

    private Set<UrlPatternFilterBean> urlPatternFilterBeans = new LinkedHashSet<>();

    public Set<UrlPatternFilterBean> getUrlPatternFilterBeans() {
        return urlPatternFilterBeans;
    }

    public void setUrlPatternFilterBeans(Set<UrlPatternFilterBean> urlPatternFilterBeans) {
        this.urlPatternFilterBeans = urlPatternFilterBeans;
    }

    @Override
    public boolean isFiltered(StructuralNode node) {
        HistoryReference hRef = node.getHistoryReference();
        if (hRef == null) {
            return true;
        }

        for (UrlPatternFilterBean urlPatternFilterBean : this.urlPatternFilterBeans) {
            switch (urlPatternFilterBean.getFilterCriteria()) {
                case INCLUDE:
                    for (Pattern pattern : urlPatternFilterBean.getUrlPatterns()) {
                        if (pattern.matcher(hRef.getURI().toString()).matches()) {
                            return true;
                        }
                    }
                    return false;
                case EXCLUDE:
                    for (Pattern pattern : urlPatternFilterBean.getUrlPatterns()) {
                        if (pattern.matcher(hRef.getURI().toString()).matches()) {
                            return false;
                        }
                    }
                    return true;
                default:
                    return true;
            }
        }
        return true;
    }
}

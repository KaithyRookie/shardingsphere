/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.governance.core.config.listener;

import org.apache.shardingsphere.governance.core.config.ConfigCenterNode;
import org.apache.shardingsphere.governance.core.event.listener.PostGovernanceRepositoryEventListener;
import org.apache.shardingsphere.governance.core.event.model.GovernanceEvent;
import org.apache.shardingsphere.governance.core.event.model.auth.AuthenticationChangedEvent;
import org.apache.shardingsphere.governance.repository.api.ConfigurationRepository;
import org.apache.shardingsphere.governance.repository.api.listener.DataChangedEvent;
import org.apache.shardingsphere.infra.auth.builtin.yaml.config.YamlAuthenticationConfiguration;
import org.apache.shardingsphere.infra.auth.builtin.yaml.swapper.AuthenticationYamlSwapper;
import org.apache.shardingsphere.infra.yaml.engine.YamlEngine;

import java.util.Collections;
import java.util.Optional;

/**
 * Authentication changed listener.
 */
public final class AuthenticationChangedListener extends PostGovernanceRepositoryEventListener<GovernanceEvent> {
    
    public AuthenticationChangedListener(final ConfigurationRepository configurationRepository) {
        super(configurationRepository, Collections.singletonList(new ConfigCenterNode().getAuthenticationPath()));
    }
    
    @Override
    protected Optional<GovernanceEvent> createEvent(final DataChangedEvent event) {
        return Optional.of(new AuthenticationChangedEvent(new AuthenticationYamlSwapper().swapToObject(YamlEngine.unmarshal(event.getValue(), YamlAuthenticationConfiguration.class))));
    }
}

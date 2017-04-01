<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
<div class="am-g error-log">
    <div class="am-u-sm-12 am-u-sm-centered">
        <#--INFO,ERROR,DEBUG,WARN-->
       <pre class="am-pre-scrollable">
        <#assign logType = ''/>
           <#list logLines as logLine>
               <#if logLine.type=='INFO'>
                   <#assign logType = 'am-text-success'/>
               <#elseif logLine.type = 'ERROR'>
                   <#assign logType = 'am-text-danger'/>
               <#elseif logLine.type = 'DEBUG'>
                   <#assign logType = 'am-link-muted'/>
               <#elseif logLine.type = 'WARN'>
                   <#assign logType = 'am-text-warning'/>
               </#if>
                   <div class="${logType}">${logLine.line}</div>
           </#list>
       </pre>
    </div>
</div>

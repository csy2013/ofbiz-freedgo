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

<#assign nowTimestamp = Static["org.ofbiz.base.util.UtilDateTime"].nowTimestamp()>
<div class="footer fixed">
    <div class="pull-right">
        Version of <strong>Beta0.1</strong> Free.
    </div>
    <div>
        <strong>Copyright</strong> YuaoQ Company &copy; 2014-2015
    </div>
</div>

<#if layoutSettings.VT_FTR_JAVASCRIPT?has_content>
  <#list layoutSettings.VT_FTR_JAVASCRIPT as javaScript>
  <script src="<@ofbizContentUrl>${StringUtil.wrapString(javaScript)}</@ofbizContentUrl>" type="text/javascript"></script>
  </#list>
</#if>

</div>
</body>
</html>
<script type="text/javascript">
    //    $(document).ready(function() {
    //        setTimeout(function () {
    //            $.gritter.add({
    //                title: 'You have two new messages',
    //                text: 'Go to <a href="mailbox.html" class="text-warning">Mailbox</a> to see who wrote to you.<br/> Check the date and today\'s tasks.',
    //                time: 2000
    //            });
    //        }, 2000);
    //    });

    $(document).ready(function () {
        var handleFormRemoteValidate = function () {
            "use strict";
            window.Parsley.addAsyncValidator('checkExistsField', function (xhr) {
                console.log(xhr.responseText);
                return !(xhr.responseText.indexOf('entityData') >= 0);
            }, 'entityFieldExists').addMessage('zh-cn', 'remote', '该值在系统中已经存在');
            var parseRequirement = function (requirement) {
                if (isNaN(+requirement))
                    return parseFloat(jQuery(requirement).val());
                else
                    return +requirement;
            };

            // Greater than validator
            window.Parsley.addValidator('gt', {
                validateString: function (value, requirement) {
                    return parseFloat(value) > parseRequirement(requirement);
                },
                priority: 32
            });

// Greater than or equal to validator
            window.Parsley.addValidator('gte', {
                validateString: function (value, requirement) {
                    return parseFloat(value) >= parseRequirement(requirement);
                },
                priority: 32
            });

// Less than validator
            window.Parsley.addValidator('lt', {
                validateString: function (value, requirement) {
                    return parseFloat(value) < parseRequirement(requirement);
                },
                priority: 32
            });

// Less than or equal to validator
            window.Parsley.addValidator('lte', {
                validateString: function (value, requirement) {
                    return parseFloat(value) <= parseRequirement(requirement);
                },
                priority: 32
            });
            window.Parsley.addValidator('dateiso', {
                validateString: function (value) {
                    return /^(\d{4})\D?(0[1-9]|1[0-2])\D?([12]\d|0[1-9]|3[01])$/.test(value);
                },
                priority: 256
            });

            var countWords = function (string) {
                return string
                        .replace(/(^\s*)|(\s*$)/gi, "")
                        .replace(/\s+/gi, " ")
                        .split(' ').length;
            };

            window.Parsley.addValidator(
                    'minwords',
                    function (value, nbWords) {
                        return countWords(value) >= nbWords;
                    }, 32)
                    .addMessage('zh-cn', 'minwords', '字数小于设定值');

            window.Parsley.addValidator(
                    'maxwords',
                    function (value, nbWords) {
                        return countWords(value) <= nbWords;
                    }, 32)
                    .addMessage('zh-cn', 'maxwords', '字数大于设置值');

            window.Parsley.addValidator(
                    'words',
                    function (value, arrayRange) {
                        var length = countWords(value);
                        return length >= arrayRange[0] && length <= arrayRange[1];
                    }, 32)
                    .addMessage('zh-cn', 'words', '字数必须在设定的范围内');
        };
        handleFormRemoteValidate();
        window.Parsley.setLocale('zh-cn');
    });

</script>

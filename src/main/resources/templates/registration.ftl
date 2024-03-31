<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1">Add new user</div>
    <#if message??> <!-- Check if the message exists before trying to display it -->
        <div class="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/registration" true/>
</@c.page>
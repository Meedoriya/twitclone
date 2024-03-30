<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Add new user
    <#if message??> <!-- Check if the message exists before trying to display it -->
        <div class="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/registration" />
</@c.page>
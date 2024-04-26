<#if session?has_content && session.SPRING_SECURITY_CONTEXT??>
    <#assign
    secContext = session.SPRING_SECURITY_CONTEXT.authentication
    user = secContext.principal
    name = user.username
    isAdmin = (secContext.authorities?seq_contains("ROLE_ADMIN")!false)
    currentUserId = user.id
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>

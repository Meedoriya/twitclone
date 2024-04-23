<#if session?? && session.SPRING_SECURITY_CONTEXT??>
    <#assign
    user = session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.getAuthorities()?seq_contains("ADMIN")
    currentUserId = user.getId()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    currentUserId = -1
    >
</#if>

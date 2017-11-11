package com.mikewerzen.automation.assistant.core.action

import com.mikewerzen.automation.assistant.core.AutomationContext

interface ActionExecutor
{
	fun execute(context: AutomationContext) : AutomationContext
}
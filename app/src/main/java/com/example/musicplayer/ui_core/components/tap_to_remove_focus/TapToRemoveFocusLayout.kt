package com.example.musicplayer.ui_core.components.tap_to_remove_focus

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ObserverModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.LocalFocusManager
import com.example.musicplayer.ui_core.modifier.optional
import com.example.musicplayer.ui_core.modifier.rippleLessClickable
import java.util.UUID


@Composable
fun TapToRemoveFocusLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val clearFocusOnTapController = remember {
        ClearFocusOnTapControllerImpl()
    }
    val focusManager = LocalFocusManager.current
    CompositionLocalProvider(
        LocalClearFocusOnTapController provides clearFocusOnTapController
    ) {
        Box(
            modifier = modifier
                .optional(clearFocusOnTapController.shouldClearFocusOnTap) {
                    rippleLessClickable(onClick = { focusManager.clearFocus() })
                },
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

fun Modifier.clearFocusOnTap(): Modifier {
    return this.then(ClearFocusOnTapRegisteringElement())
}

private val LocalClearFocusOnTapController =
    staticCompositionLocalOf<ClearFocusOnTapController?> { null }

private class ClearFocusOnTapRegisteringElement :
    ModifierNodeElement<CleanFocusOnTapRegisteringNode>() {

    private val id = UUID.randomUUID().toString()

    override fun create(): CleanFocusOnTapRegisteringNode {
        return CleanFocusOnTapRegisteringNode(id)
    }

    override fun update(node: CleanFocusOnTapRegisteringNode) {
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "ClearFocusOnTap"
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? CleanFocusOnTapRegisteringNode ?: return false
        return this.id == otherModifier.id
    }

}

private class CleanFocusOnTapRegisteringNode(
    var id: String
) : Modifier.Node(),
    CompositionLocalConsumerModifierNode,
    ObserverModifierNode {

    private var controller: ClearFocusOnTapController? = null

    override fun onAttach() {
        super.onAttach()
        updateController()
    }

    override fun onDetach() {
        super.onDetach()
        controller?.remove(id)
    }

    override fun onObservedReadsChanged() {
        updateController()
    }

    private fun updateController() {
        val newController = currentValueOf(LocalClearFocusOnTapController)
        if (newController != controller) {
            controller?.remove(id)
            controller = newController
            controller?.add(id)
        }
    }
}

private interface ClearFocusOnTapController {

    val shouldClearFocusOnTap: Boolean

    fun add(id: String)

    fun remove(id: String)

}


private class ClearFocusOnTapControllerImpl : ClearFocusOnTapController {

    private val triggers = mutableStateListOf<String>()

    override val shouldClearFocusOnTap by derivedStateOf { triggers.isNotEmpty() }

    override fun add(id: String) {
        triggers.add(id)
    }

    override fun remove(id: String) {
        triggers.remove(id)
    }

}

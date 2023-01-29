package me.kikugie.techutils.event;

import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import me.kikugie.techutils.config.Configs;
import me.kikugie.techutils.config.InGameNotifier;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;


public class KeyCallbacks {
    public static void init() {
        Configs.LitematicConfigs.ROTATE_PLACEMENT.getKeybind().setCallback(new RotatePlacementCallback());
        Configs.LitematicConfigs.MIRROR_PLACEMENT.getKeybind().setCallback(new MirrorPlacementCallback());
    }

    private static class RotatePlacementCallback implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == Configs.LitematicConfigs.ROTATE_PLACEMENT.getKeybind()) {
                var placement = DataManager.getSchematicPlacementManager().getSelectedSchematicPlacement();
                if (placement == null) return false;
                placement.setRotation(placement.getRotation().rotate(BlockRotation.CLOCKWISE_90), InGameNotifier.INSTANCE);
                return true;
            }
            return false;
        }
    }

    private static class MirrorPlacementCallback implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            if (key == Configs.LitematicConfigs.MIRROR_PLACEMENT.getKeybind()) {
                var placement = DataManager.getSchematicPlacementManager().getSelectedSchematicPlacement();
                if (placement == null) return false;
                BlockMirror mirror = switch (placement.getMirror()) {
                    case NONE -> BlockMirror.LEFT_RIGHT;
                    case LEFT_RIGHT -> BlockMirror.FRONT_BACK;
                    case FRONT_BACK -> BlockMirror.NONE;
                };
                placement.setMirror(mirror, InGameNotifier.INSTANCE);
                return true;
            }
            return false;
        }
    }
}

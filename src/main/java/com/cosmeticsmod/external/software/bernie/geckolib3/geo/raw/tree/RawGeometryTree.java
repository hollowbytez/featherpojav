/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.MinecraftGeometry
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup
 *  com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawGeometryTree
 *  com.cosmeticsmod.morecosmetics.MoreCosmetics
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree;

import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.Bone;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.MinecraftGeometry;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.ModelProperties;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.pojo.RawGeoModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.geo.raw.tree.RawBoneGroup;
import com.cosmeticsmod.morecosmetics.MoreCosmetics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/*
 * Exception performing whole class analysis ignored.
 */
public class RawGeometryTree {
    public HashMap<String, RawBoneGroup> topLevelBones = new HashMap();
    public ModelProperties properties;

    public static RawGeometryTree parseHierarchy(RawGeoModel model, String name) {
        RawGeometryTree hierarchy = new RawGeometryTree();
        MinecraftGeometry geometry = model.getMinecraftGeometry()[0];
        hierarchy.properties = geometry.getProperties();
        ArrayList<Bone> bones = new ArrayList<Bone>(Arrays.asList(geometry.getBones()));
        int index = bones.size() - 1;
        int loopsWithoutChange = 0;
        while (true) {
            if (++loopsWithoutChange > 10000) {
                MoreCosmetics.LOGGER.warn("Some bones in " + name + " do not have existing parents: ");
                MoreCosmetics.LOGGER.warn(bones.stream().map(x -> x.getName()).collect(Collectors.joining(", ")));
                break;
            }
            Bone bone = (Bone)bones.get(index);
            if (!RawGeometryTree.hasParent((Bone)bone)) {
                hierarchy.topLevelBones.put(bone.getName(), new RawBoneGroup(bone));
                bones.remove(bone);
                loopsWithoutChange = 0;
            } else {
                RawBoneGroup groupFromHierarchy = RawGeometryTree.getGroupFromHierarchy((RawGeometryTree)hierarchy, (String)bone.getParent());
                if (groupFromHierarchy != null) {
                    groupFromHierarchy.children.put(bone.getName(), new RawBoneGroup(bone));
                    bones.remove(bone);
                    loopsWithoutChange = 0;
                }
            }
            if (index == 0) {
                index = bones.size() - 1;
                if (index != -1) continue;
                break;
            }
            --index;
        }
        return hierarchy;
    }

    public static boolean hasParent(Bone bone) {
        return bone.getParent() != null;
    }

    public static RawBoneGroup getGroupFromHierarchy(RawGeometryTree hierarchy, String bone) {
        HashMap<String, RawBoneGroup> flatList = new HashMap<String, RawBoneGroup>();
        for (RawBoneGroup group : hierarchy.topLevelBones.values()) {
            flatList.put(group.selfBone.getName(), group);
            RawGeometryTree.traverse(flatList, (RawBoneGroup)group);
        }
        return (RawBoneGroup)flatList.get(bone);
    }

    public static void traverse(HashMap<String, RawBoneGroup> flatList, RawBoneGroup group) {
        for (RawBoneGroup child : group.children.values()) {
            flatList.put(child.selfBone.getName(), child);
            RawGeometryTree.traverse(flatList, (RawBoneGroup)child);
        }
    }
}


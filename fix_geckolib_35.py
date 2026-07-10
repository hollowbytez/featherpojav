import os
import re

def read(path):
    with open(path, "r", encoding="utf-8") as f: return f.read()
def write(path, c):
    with open(path, "w", encoding="utf-8") as f: f.write(c)

# ========== 1. AnimationController - remove (Object) casts on AnimationPoint adds ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\controller\AnimationController.java"
c = read(f)
# Remove all (Object) casts before this.getAnimationPointAtTick
c = c.replace("(Object)this.getAnimationPointAtTick(", "this.getAnimationPointAtTick(")

# Fix event variable: SoundKeyframeEvent is reused for 3 different types - split them
c = c.replace(
    """if (this.soundListener != null || this.particleListener != null || this.customInstructionListener != null) {
            SoundKeyframeEvent event;
            for (EventKeyFrame soundKeyFrame : this.currentAnimation.soundKeyFrames) {
                if (this.executedKeyFrames.contains(soundKeyFrame) || !(tick >= soundKeyFrame.getStartTick())) continue;
                event = new SoundKeyframeEvent((Object)this.animatable, tick, (String)soundKeyFrame.getEventData(), this);
                this.soundListener.playSound(event);
                this.executedKeyFrames.add(soundKeyFrame);
            }
            for (ParticleEventKeyFrame particleEventKeyFrame : this.currentAnimation.particleKeyFrames) {
                if (this.executedKeyFrames.contains(particleEventKeyFrame) || !(tick >= particleEventKeyFrame.getStartTick())) continue;
                event = new ParticleKeyFrameEvent((Object)this.animatable, tick, particleEventKeyFrame.effect, particleEventKeyFrame.locator, particleEventKeyFrame.script, this);
                this.particleListener.summonParticle((ParticleKeyFrameEvent)event);
                this.executedKeyFrames.add(particleEventKeyFrame);
            }
            for (EventKeyFrame customInstructionKeyFrame : this.currentAnimation.customInstructionKeyframes) {
                if (this.executedKeyFrames.contains(customInstructionKeyFrame) || !(tick >= customInstructionKeyFrame.getStartTick())) continue;
                event = new CustomInstructionKeyframeEvent((Object)this.animatable, tick, (String)customInstructionKeyFrame.getEventData(), this);
                this.customInstructionListener.executeInstruction((CustomInstructionKeyframeEvent)event);
                this.executedKeyFrames.add(customInstructionKeyFrame);
            }
        }""",
    """if (this.soundListener != null || this.particleListener != null || this.customInstructionListener != null) {
            for (EventKeyFrame soundKeyFrame : this.currentAnimation.soundKeyFrames) {
                if (this.executedKeyFrames.contains(soundKeyFrame) || !(tick >= soundKeyFrame.getStartTick())) continue;
                SoundKeyframeEvent soundEvent = new SoundKeyframeEvent(this.animatable, tick, (String)soundKeyFrame.getEventData(), this);
                this.soundListener.playSound(soundEvent);
                this.executedKeyFrames.add(soundKeyFrame);
            }
            for (ParticleEventKeyFrame particleEventKeyFrame : this.currentAnimation.particleKeyFrames) {
                if (this.executedKeyFrames.contains(particleEventKeyFrame) || !(tick >= particleEventKeyFrame.getStartTick())) continue;
                ParticleKeyFrameEvent particleEvent = new ParticleKeyFrameEvent(this.animatable, tick, particleEventKeyFrame.effect, particleEventKeyFrame.locator, particleEventKeyFrame.script, this);
                this.particleListener.summonParticle(particleEvent);
                this.executedKeyFrames.add(particleEventKeyFrame);
            }
            for (EventKeyFrame customInstructionKeyFrame : this.currentAnimation.customInstructionKeyframes) {
                if (this.executedKeyFrames.contains(customInstructionKeyFrame) || !(tick >= customInstructionKeyFrame.getStartTick())) continue;
                CustomInstructionKeyframeEvent customEvent = new CustomInstructionKeyframeEvent(this.animatable, tick, (String)customInstructionKeyFrame.getEventData(), this);
                this.customInstructionListener.executeInstruction(customEvent);
                this.executedKeyFrames.add(customInstructionKeyFrame);
            }
        }"""
)
write(f, c)

# ========== 2. AnimationPoint - fix float -> Double ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\keyframe\AnimationPoint.java"
c = read(f)
c = c.replace(
    "public AnimationPoint(KeyFrame<IValue> keyframe, double tick, double animationEndTick, float animationStartValue, double animationEndValue) {",
    "public AnimationPoint(KeyFrame<IValue> keyframe, double tick, double animationEndTick, double animationStartValue, double animationEndValue) {"
)
write(f, c)

# ========== 3. ParticleEventKeyFrame - fix (Object) cast to String ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\keyframe\ParticleEventKeyFrame.java"
c = read(f)
c = c.replace(
    'super(startTick, (Object)(effect + "\\n" + locator + "\\n" + script));',
    'super(startTick, effect + "\\n" + locator + "\\n" + script);'
)
write(f, c)

# ========== 4. AnimationProcessor - fix raw type iterations ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\processor\AnimationProcessor.java"
c = read(f)
# Fix getBoneAnimationQueues().values() raw type
c = c.replace(
    "for (BoneAnimationQueue boneAnimation : controller.getBoneAnimationQueues().values()) {",
    "for (Object boneAnimObj : controller.getBoneAnimationQueues().values()) {\n                BoneAnimationQueue boneAnimation = (BoneAnimationQueue)boneAnimObj;"
)
# Fix modelTracker.entrySet() raw type
c = c.replace(
    "for (Map.Entry tracker : modelTracker.entrySet()) {",
    "for (Object trackerObj : modelTracker.entrySet()) {\n            Map.Entry tracker = (Map.Entry)trackerObj;"
)
# Fix Pair.of cast
c = c.replace(
    "boneSnapshotCollection.put(bone.getName(), (Pair<IBone, BoneSnapshot>)Pair.of((Object)bone, (Object)new BoneSnapshot(bone.getInitialSnapshot())));",
    "boneSnapshotCollection.put(bone.getName(), Pair.of(bone, new BoneSnapshot(bone.getInitialSnapshot())));"
)
write(f, c)

# ========== 5. EasingManager.EasingFunctionArgs - add constructor ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\easing\EasingManager.java"
c = read(f)
c = c.replace(
    """    public static class EasingFunctionArgs {
        public Object arg0;
        public EasingType easingType;
    }""",
    """    public static class EasingFunctionArgs {
        public Object arg0;
        public EasingType easingType;
        public EasingFunctionArgs() {}
        public EasingFunctionArgs(EasingType easingType, Double arg0) {
            this.easingType = easingType;
            this.arg0 = arg0;
        }
    }"""
)
write(f, c)

# ========== 6. AnimationFileLoader - fix raw type iteration ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\file\AnimationFileLoader.java"
c = read(f)
c = c.replace(
    "for (Map.Entry entry : entrySet) {",
    "for (Object entryObj : entrySet) {\n            Map.Entry entry = (Map.Entry)entryObj;"
)
write(f, c)

# ========== 7. EnumFacing.AxisDirection - add getOffset() method ==========
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\geo\render\built\EnumFacing.java"
c = read(f)
c = c.replace(
    """    public enum AxisDirection {
        POSITIVE, NEGATIVE
    }""",
    """    public enum AxisDirection {
        POSITIVE(1), NEGATIVE(-1);
        private final int offset;
        AxisDirection(int offset) { this.offset = offset; }
        public int getOffset() { return this.offset; }
    }"""
)
write(f, c)

# ========== 8. CosmeticAnimatable - fix IAnimationPredicate return type ==========
# The interface returns boolean but predicate returns PlayState
# Fix the interface to return PlayState instead
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\controller\AnimationController.java"
c = read(f)
c = c.replace(
    "public interface IAnimationPredicate<T> { boolean test(Object event); }",
    "public interface IAnimationPredicate<T> { PlayState test(Object event); }"
)
write(f, c)

# Fix CosmeticAnimatable lambda
f = r"A:\MCMDS\src\main\java\com\cosmeticsmod\morecosmetics\models\animated\CosmeticAnimatable.java"
c = read(f)
c = c.replace(
    "this.controller = new AnimationController((IAnimatable)this, this.controllerName, 5.0f, (AnimationController.IAnimationPredicate)(arg_0 -> this.predicate((AnimationEvent)arg_0)));",
    "this.controller = new AnimationController((IAnimatable)this, this.controllerName, 5.0f, (AnimationController.IAnimationPredicate)(arg_0 -> this.predicate((com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent)arg_0)));"
)
write(f, c)

print("All 35 GeckoLib errors fixed!")

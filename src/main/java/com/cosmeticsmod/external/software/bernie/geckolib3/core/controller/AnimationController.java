/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue
 *  com.cosmeticsmod.external.com.eliotlash.molang.MolangParser
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.AnimationState
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.ConstantValue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.PlayState
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.AnimationBuilder
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController$IAnimationPredicate
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController$ICustomInstructionListener
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController$IParticleListener
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController$ISoundListener
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController$ModelFetcher
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.ParticleKeyFrameEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.SoundKeyframeEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimationQueue
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrameLocation
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot
 *  com.cosmeticsmod.external.software.bernie.geckolib3.core.util.Axis
 *  org.apache.commons.lang3.tuple.Pair
 */
package com.cosmeticsmod.external.software.bernie.geckolib3.core.controller;

import com.cosmeticsmod.external.com.eliotlash.mclib.math.IValue;
import com.cosmeticsmod.external.com.eliotlash.molang.MolangParser;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.AnimationState;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.ConstantValue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatable;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.IAnimatableModel;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.PlayState;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.Animation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.builder.AnimationBuilder;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.controller.AnimationController;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.easing.EasingType;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.CustomInstructionKeyframeEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.AnimationPoint;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.BoneAnimationQueue;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.EventKeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.KeyFrameLocation;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.ParticleEventKeyFrame;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.keyframe.VectorKeyFrameList;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.processor.IBone;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import com.cosmeticsmod.external.software.bernie.geckolib3.core.util.Axis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class AnimationController<T extends IAnimatable> {
    static List<ModelFetcher<?>> modelFetchers = new ArrayList();
    protected T animatable;
    protected IAnimationPredicate<T> animationPredicate;
    private final String name;
    protected AnimationState animationState = AnimationState.Stopped;
    public double transitionLengthTicks;
    private ISoundListener<T> soundListener;
    private IParticleListener<T> particleListener;
    private ICustomInstructionListener<T> customInstructionListener;
    public boolean isJustStarting = false;
    private final HashMap<String, BoneAnimationQueue> boneAnimationQueues = new HashMap();
    public double tickOffset;
    protected Queue<Animation> animationQueue = new LinkedList();
    protected Animation currentAnimation;
    protected AnimationBuilder currentAnimationBuilder = new AnimationBuilder();
    protected boolean shouldResetTick = false;
    private final HashMap<String, BoneSnapshot> boneSnapshots = new HashMap();
    private boolean justStopped = false;
    protected boolean justStartedTransition = false;
    public Function<Double, Double> customEasingMethod;
    protected boolean needsAnimationReload = false;
    public double animationSpeed = 1.0;
    private final Set<EventKeyFrame<?>> executedKeyFrames = new HashSet();
    public EasingType easingType = EasingType.NONE;

    public static void addModelFetcher(ModelFetcher<?> fetcher) {
        modelFetchers.add(fetcher);
    }

    public static void removeModelFetcher(ModelFetcher<?> fetcher) {
        Objects.requireNonNull(fetcher);
        modelFetchers.remove(fetcher);
    }

    public void setAnimation(AnimationBuilder builder) {
        IAnimatableModel model = this.getModel(this.animatable);
        if (model != null) {
            if (builder == null || builder.getRawAnimationList().size() == 0) {
                this.animationState = AnimationState.Stopped;
            } else if (!builder.getRawAnimationList().equals(this.currentAnimationBuilder.getRawAnimationList()) || this.needsAnimationReload) {
                AtomicBoolean encounteredError = new AtomicBoolean(false);
                LinkedList animations = builder.getRawAnimationList().stream().map(rawAnimation -> {
                    Animation animation = model.getAnimation(rawAnimation.animationName, this.animatable);
                    if (animation == null) {
                        System.out.printf("Could not load animation: %s. Is it missing?", rawAnimation.animationName);
                        encounteredError.set(true);
                    }
                    if (animation != null && rawAnimation.loop != null) {
                        animation.loop = rawAnimation.loop;
                    }
                    return animation;
                }).collect(Collectors.toCollection(LinkedList::new));
                if (encounteredError.get()) {
                    return;
                }
                this.animationQueue = animations;
                this.currentAnimationBuilder = builder;
                this.shouldResetTick = true;
                this.animationState = AnimationState.Transitioning;
                this.justStartedTransition = true;
                this.needsAnimationReload = false;
            }
        }
    }

    public AnimationController(T animatable, String name, float transitionLengthTicks, IAnimationPredicate<T> animationPredicate) {
        this.animatable = animatable;
        this.name = name;
        this.transitionLengthTicks = transitionLengthTicks;
        this.animationPredicate = animationPredicate;
        this.tickOffset = 0.0;
    }

    public AnimationController(T animatable, String name, float transitionLengthTicks, EasingType easingtype, IAnimationPredicate<T> animationPredicate) {
        this.animatable = animatable;
        this.name = name;
        this.transitionLengthTicks = transitionLengthTicks;
        this.easingType = easingtype;
        this.animationPredicate = animationPredicate;
        this.tickOffset = 0.0;
    }

    public AnimationController(T animatable, String name, float transitionLengthTicks, Function<Double, Double> customEasingMethod, IAnimationPredicate<T> animationPredicate) {
        this.animatable = animatable;
        this.name = name;
        this.transitionLengthTicks = transitionLengthTicks;
        this.customEasingMethod = customEasingMethod;
        this.easingType = EasingType.CUSTOM;
        this.animationPredicate = animationPredicate;
        this.tickOffset = 0.0;
    }

    public String getName() {
        return this.name;
    }

    public Animation getCurrentAnimation() {
        return this.currentAnimation;
    }

    public AnimationState getAnimationState() {
        return this.animationState;
    }

    public HashMap<String, BoneAnimationQueue> getBoneAnimationQueues() {
        return this.boneAnimationQueues;
    }

    public void registerSoundListener(ISoundListener<T> soundListener) {
        this.soundListener = soundListener;
    }

    public void registerParticleListener(IParticleListener<T> particleListener) {
        this.particleListener = particleListener;
    }

    public void registerCustomInstructionListener(ICustomInstructionListener<T> customInstructionListener) {
        this.customInstructionListener = customInstructionListener;
    }

    public void process(double tick, AnimationEvent<T> event, List<IBone> modelRendererList, HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection, MolangParser parser, boolean crashWhenCantFindBone) {
        Animation animation;
        IAnimatableModel model;
        parser.setValue("query.life_time", tick / 20.0);
        if (this.currentAnimation != null && (model = this.getModel(this.animatable)) != null && (animation = model.getAnimation(this.currentAnimation.animationName, this.animatable)) != null) {
            boolean loop = this.currentAnimation.loop;
            this.currentAnimation = animation;
            this.currentAnimation.loop = loop;
        }
        this.createInitialQueues(modelRendererList);
        double actualTick = tick;
        tick = this.adjustTick(tick);
        if (this.animationState == AnimationState.Transitioning && tick >= this.transitionLengthTicks) {
            this.shouldResetTick = true;
            this.animationState = AnimationState.Running;
            tick = this.adjustTick(actualTick);
        }
        assert (tick >= 0.0) : "GeckoLib: Tick was less than zero";
        PlayState playState = this.testAnimationPredicate(event);
        if (playState == PlayState.STOP || this.currentAnimation == null && this.animationQueue.size() == 0) {
            this.animationState = AnimationState.Stopped;
            this.justStopped = true;
            return;
        }
        if (this.justStartedTransition && (this.shouldResetTick || this.justStopped)) {
            this.justStopped = false;
            tick = this.adjustTick(actualTick);
        } else if (this.currentAnimation == null && this.animationQueue.size() != 0) {
            this.shouldResetTick = true;
            this.animationState = AnimationState.Transitioning;
            this.justStartedTransition = true;
            this.needsAnimationReload = false;
            tick = this.adjustTick(actualTick);
        } else if (this.animationState != AnimationState.Transitioning) {
            this.animationState = AnimationState.Running;
        }
        if (this.animationState == AnimationState.Transitioning) {
            if (tick == 0.0 || this.isJustStarting) {
                this.justStartedTransition = false;
                this.currentAnimation = (Animation)this.animationQueue.poll();
                this.resetEventKeyFrames();
                this.saveSnapshotsForAnimation(this.currentAnimation, boneSnapshotCollection);
            }
            if (this.currentAnimation != null) {
                this.setAnimTime(parser, 0.0);
                for (BoneAnimation boneAnimation : this.currentAnimation.boneAnimations) {
                    AnimationPoint zPoint;
                    AnimationPoint yPoint;
                    AnimationPoint xPoint;
                    BoneAnimationQueue boneAnimationQueue = (BoneAnimationQueue)this.boneAnimationQueues.get(boneAnimation.boneName);
                    BoneSnapshot boneSnapshot = (BoneSnapshot)this.boneSnapshots.get(boneAnimation.boneName);
                    Optional<IBone> first = modelRendererList.stream().filter(x -> x.getName().equals(boneAnimation.boneName)).findFirst();
                    if (!first.isPresent()) {
                        if (!crashWhenCantFindBone) continue;
                        throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
                    }
                    BoneSnapshot initialSnapshot = first.get().getInitialSnapshot();
                    assert (boneSnapshot != null) : "Bone snapshot was null";
                    VectorKeyFrameList rotationKeyFrames = boneAnimation.rotationKeyFrames;
                    VectorKeyFrameList positionKeyFrames = boneAnimation.positionKeyFrames;
                    VectorKeyFrameList scaleKeyFrames = boneAnimation.scaleKeyFrames;
                    if (!rotationKeyFrames.xKeyFrames.isEmpty()) {
                        xPoint = this.getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, 0.0, true, Axis.X);
                        yPoint = this.getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, 0.0, true, Axis.Y);
                        zPoint = this.getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, 0.0, true, Axis.Z);
                        boneAnimationQueue.rotationXQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.rotationValueX - initialSnapshot.rotationValueX, xPoint.animationStartValue.doubleValue()));
                        boneAnimationQueue.rotationYQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.rotationValueY - initialSnapshot.rotationValueY, yPoint.animationStartValue.doubleValue()));
                        boneAnimationQueue.rotationZQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.rotationValueZ - initialSnapshot.rotationValueZ, zPoint.animationStartValue.doubleValue()));
                    }
                    if (!positionKeyFrames.xKeyFrames.isEmpty()) {
                        xPoint = this.getAnimationPointAtTick(positionKeyFrames.xKeyFrames, 0.0, false, Axis.X);
                        yPoint = this.getAnimationPointAtTick(positionKeyFrames.yKeyFrames, 0.0, false, Axis.Y);
                        zPoint = this.getAnimationPointAtTick(positionKeyFrames.zKeyFrames, 0.0, false, Axis.Z);
                        boneAnimationQueue.positionXQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.positionOffsetX, xPoint.animationStartValue.doubleValue()));
                        boneAnimationQueue.positionYQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.positionOffsetY, yPoint.animationStartValue.doubleValue()));
                        boneAnimationQueue.positionZQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.positionOffsetZ, zPoint.animationStartValue.doubleValue()));
                    }
                    if (scaleKeyFrames.xKeyFrames.isEmpty()) continue;
                    xPoint = this.getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, 0.0, false, Axis.X);
                    yPoint = this.getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, 0.0, false, Axis.Y);
                    zPoint = this.getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, 0.0, false, Axis.Z);
                    boneAnimationQueue.scaleXQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.scaleValueX, xPoint.animationStartValue.doubleValue()));
                    boneAnimationQueue.scaleYQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.scaleValueY, yPoint.animationStartValue.doubleValue()));
                    boneAnimationQueue.scaleZQueue.add(new AnimationPoint(null, tick, this.transitionLengthTicks, boneSnapshot.scaleValueZ, zPoint.animationStartValue.doubleValue()));
                }
            }
        } else if (this.getAnimationState() == AnimationState.Running) {
            this.processCurrentAnimation(tick, actualTick, parser, crashWhenCantFindBone);
        }
    }

    private void setAnimTime(MolangParser parser, double tick) {
        parser.setValue("query.anim_time", tick / 20.0);
    }

    private IAnimatableModel<T> getModel(T animatable) {
        for (ModelFetcher modelFetcher : modelFetchers) {
            IAnimatableModel model = (IAnimatableModel)modelFetcher.apply(animatable);
            if (model == null) continue;
            return model;
        }
        System.out.printf("Could not find suitable model for animatable of type %s. Did you register a Model Fetcher?%n", animatable.getClass());
        return null;
    }

    protected PlayState testAnimationPredicate(AnimationEvent<T> event) {
        return this.animationPredicate.test(event);
    }

    private void saveSnapshotsForAnimation(Animation animation, HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection) {
        for (Pair<IBone, BoneSnapshot> snapshot : boneSnapshotCollection.values()) {
            if (animation == null || animation.boneAnimations == null || !animation.boneAnimations.stream().anyMatch(x -> x.boneName.equals(((IBone)snapshot.getLeft()).getName()))) continue;
            this.boneSnapshots.put(((IBone)snapshot.getLeft()).getName(), new BoneSnapshot((BoneSnapshot)snapshot.getRight()));
        }
    }

    private void processCurrentAnimation(double tick, double actualTick, MolangParser parser, boolean crashWhenCantFindBone) {
        assert (this.currentAnimation != null);
        if (tick >= this.currentAnimation.animationLength) {
            this.resetEventKeyFrames();
            if (!this.currentAnimation.loop) {
                Animation peek = (Animation)this.animationQueue.peek();
                if (peek == null) {
                    this.animationState = AnimationState.Stopped;
                    return;
                }
                this.animationState = AnimationState.Transitioning;
                this.shouldResetTick = true;
                this.currentAnimation = (Animation)this.animationQueue.peek();
            } else {
                this.shouldResetTick = true;
                tick = this.adjustTick(actualTick);
            }
        }
        this.setAnimTime(parser, tick);
        List boneAnimations = this.currentAnimation.boneAnimations;
        for (Object boneAnimObj : boneAnimations) {
            BoneAnimation boneAnimation = (BoneAnimation)boneAnimObj;
            BoneAnimationQueue boneAnimationQueue = (BoneAnimationQueue)this.boneAnimationQueues.get(boneAnimation.boneName);
            if (boneAnimationQueue == null) {
                if (!crashWhenCantFindBone) continue;
                throw new RuntimeException("Could not find bone: " + boneAnimation.boneName);
            }
            VectorKeyFrameList rotationKeyFrames = boneAnimation.rotationKeyFrames;
            VectorKeyFrameList positionKeyFrames = boneAnimation.positionKeyFrames;
            VectorKeyFrameList scaleKeyFrames = boneAnimation.scaleKeyFrames;
            if (!rotationKeyFrames.xKeyFrames.isEmpty()) {
                boneAnimationQueue.rotationXQueue.add(this.getAnimationPointAtTick(rotationKeyFrames.xKeyFrames, tick, true, Axis.X));
                boneAnimationQueue.rotationYQueue.add(this.getAnimationPointAtTick(rotationKeyFrames.yKeyFrames, tick, true, Axis.Y));
                boneAnimationQueue.rotationZQueue.add(this.getAnimationPointAtTick(rotationKeyFrames.zKeyFrames, tick, true, Axis.Z));
            }
            if (!positionKeyFrames.xKeyFrames.isEmpty()) {
                boneAnimationQueue.positionXQueue.add(this.getAnimationPointAtTick(positionKeyFrames.xKeyFrames, tick, false, Axis.X));
                boneAnimationQueue.positionYQueue.add(this.getAnimationPointAtTick(positionKeyFrames.yKeyFrames, tick, false, Axis.Y));
                boneAnimationQueue.positionZQueue.add(this.getAnimationPointAtTick(positionKeyFrames.zKeyFrames, tick, false, Axis.Z));
            }
            if (scaleKeyFrames.xKeyFrames.isEmpty()) continue;
            boneAnimationQueue.scaleXQueue.add(this.getAnimationPointAtTick(scaleKeyFrames.xKeyFrames, tick, false, Axis.X));
            boneAnimationQueue.scaleYQueue.add(this.getAnimationPointAtTick(scaleKeyFrames.yKeyFrames, tick, false, Axis.Y));
            boneAnimationQueue.scaleZQueue.add(this.getAnimationPointAtTick(scaleKeyFrames.zKeyFrames, tick, false, Axis.Z));
        }
        if (this.soundListener != null || this.particleListener != null || this.customInstructionListener != null) {
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
        }
        if (this.transitionLengthTicks == 0.0 && this.shouldResetTick && this.animationState == AnimationState.Transitioning) {
            this.currentAnimation = (Animation)this.animationQueue.poll();
        }
    }

    private void createInitialQueues(List<IBone> modelRendererList) {
        this.boneAnimationQueues.clear();
        for (IBone modelRenderer : modelRendererList) {
            this.boneAnimationQueues.put(modelRenderer.getName(), new BoneAnimationQueue(modelRenderer));
        }
    }

    protected double adjustTick(double tick) {
        if (this.shouldResetTick) {
            if (this.getAnimationState() == AnimationState.Transitioning) {
                this.tickOffset = tick;
            } else if (this.getAnimationState() == AnimationState.Running) {
                this.tickOffset = tick;
            }
            this.shouldResetTick = false;
            return 0.0;
        }
        return this.animationSpeed * Math.max(tick - this.tickOffset, 0.0);
    }

    private AnimationPoint getAnimationPointAtTick(List<KeyFrame<IValue>> frames, double tick, boolean isRotation, Axis axis) {
        KeyFrameLocation location = this.getCurrentKeyFrameLocation(frames, tick);
        KeyFrame currentFrame = location.currentFrame;
        double startValue = ((IValue)currentFrame.getStartValue()).get();
        double endValue = ((IValue)currentFrame.getEndValue()).get();
        if (isRotation) {
            if (!(currentFrame.getStartValue() instanceof ConstantValue)) {
                startValue = Math.toRadians(startValue);
                if (axis == Axis.X || axis == Axis.Y) {
                    startValue *= -1.0;
                }
            }
            if (!(currentFrame.getEndValue() instanceof ConstantValue)) {
                endValue = Math.toRadians(endValue);
                if (axis == Axis.X || axis == Axis.Y) {
                    endValue *= -1.0;
                }
            }
        }
        return new AnimationPoint(currentFrame, Double.valueOf(location.currentTick), currentFrame.getLength(), Double.valueOf(startValue), Double.valueOf(endValue));
    }

    private KeyFrameLocation<KeyFrame<IValue>> getCurrentKeyFrameLocation(List<KeyFrame<IValue>> frames, double ageInTicks) {
        double totalTimeTracker = 0.0;
        for (KeyFrame<IValue> frame : frames) {
            if (!((totalTimeTracker += frame.getLength().doubleValue()) > ageInTicks)) continue;
            double tick = ageInTicks - (totalTimeTracker - frame.getLength());
            return new KeyFrameLocation(frame, tick);
        }
        return new KeyFrameLocation(frames.get(frames.size() - 1), ageInTicks);
    }

    private void resetEventKeyFrames() {
        this.executedKeyFrames.clear();
    }

    public void markNeedsReload() {
        this.needsAnimationReload = true;
    }

    public void clearAnimationCache() {
        this.currentAnimationBuilder = new AnimationBuilder();
    }

    public double getAnimationSpeed() {
        return this.animationSpeed;
    }

    public void setAnimationSpeed(double animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public interface IAnimationPredicate<T> { PlayState test(Object event); }
    public interface ISoundListener<T> { void playSound(Object event); }
    public interface IParticleListener<T> { void summonParticle(Object event); }
    public interface ICustomInstructionListener<T> { void executeInstruction(Object event); }
    public interface ModelFetcher<T> { Object apply(T t); }
}


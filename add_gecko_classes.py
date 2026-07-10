import os

def inject_before_last_brace(path, content):
    with open(path, "r", encoding="utf-8") as f:
        lines = f.readlines()
        
    last_brace = -1
    for i in range(len(lines)-1, -1, -1):
        if "}" in lines[i]:
            last_brace = i
            break
            
    if last_brace != -1:
        lines.insert(last_brace, content)
        with open(path, "w", encoding="utf-8") as f:
            f.writelines(lines)

# AnimationController
path1 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\controller\AnimationController.java"
inject_before_last_brace(path1, """
    public interface IAnimationPredicate<T> { boolean test(Object event); }
    public interface ISoundListener<T> { void playSound(Object event); }
    public interface IParticleListener<T> { void summonParticle(Object event); }
    public interface ICustomInstructionListener<T> { void executeInstruction(Object event); }
    public interface ModelFetcher<T> { Object apply(T t); }
""")

# EasingManager
path2 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\core\easing\EasingManager.java"
inject_before_last_brace(path2, """
    public static class EasingFunctionArgs {
        public Object arg0;
        public EasingType easingType;
    }
""")

# EnumFacing
path3 = r"A:\MCMDS\src\main\java\com\cosmeticsmod\external\software\bernie\geckolib3\geo\render\built\EnumFacing.java"
inject_before_last_brace(path3, """
    public enum Axis {
        X, Y, Z;
        public boolean isHorizontal() {
            return this == X || this == Z;
        }
        public boolean isVertical() {
            return this == Y;
        }
    }
    public enum AxisDirection {
        POSITIVE, NEGATIVE
    }
""")

print("Injected GeckoLib inner classes.")

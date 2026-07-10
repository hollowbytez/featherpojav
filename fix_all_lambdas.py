import os
import re

SRC_DIRS = [
    r"A:\MCMDS\src\main\java\v1_21",
    r"A:\MCMDS\src\main\java\com\cosmeticsmod"
]

count = 0
for src_dir in SRC_DIRS:
    for root, _, files in os.walk(src_dir):
        for fn in files:
            if not fn.endswith(".java"):
                continue
            path = os.path.join(root, fn)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()
            
            # Find all lambda$XXX$NNN patterns and rename them
            lambdas = set(re.findall(r'lambda\$(\w+)\$(\d+)', content))
            if lambdas:
                for method_name, num in lambdas:
                    old = f"lambda${method_name}${num}"
                    new = f"synth_{method_name}_{num}"
                    if old in content:
                        content = content.replace(old, new)
                        count += 1
                
                with open(path, "w", encoding="utf-8") as f:
                    f.write(content)

print(f"Renamed {count} synthetic lambda methods.")

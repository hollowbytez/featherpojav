import os
import re

MAPPINGS_FILE = r"C:\Users\minha\.gradle\caches\fabric-loom\1.21.1\net.fabricmc.yarn.1_21_1.1.21.1+build.2-v2\mappings.tiny"
SRC_DIRS = [
    r"A:\MCMDS\src\main\java\v1_21",
    r"A:\MCMDS\src\main\java\com\cosmeticsmod"
]

def load_mappings():
    class_map = {}
    field_map = {}
    method_map = {}
    
    with open(MAPPINGS_FILE, 'r', encoding='utf-8') as f:
        lines = f.readlines()
        
    for line in lines:
        parts = line.strip('\n').split('\t')
        if line.startswith('c\t'):
            if len(parts) >= 4:
                intermediary = parts[2].split('/')[-1]
                named = parts[3].split('/')[-1]
                if intermediary.startswith("class_"):
                    class_map[intermediary] = named
        elif line.startswith('\tf\t'):
            if len(parts) >= 6:
                intermediary = parts[4]
                named = parts[5]
                if intermediary.startswith("field_"):
                    field_map[intermediary] = named
        elif line.startswith('\tm\t'):
            if len(parts) >= 6:
                intermediary = parts[4]
                named = parts[5]
                if intermediary.startswith("method_"):
                    method_map[intermediary] = named
                    
    return class_map, field_map, method_map

def apply_mappings(class_map, field_map, method_map):
    for src_dir in SRC_DIRS:
        for root, _, files in os.walk(src_dir):
            for file in files:
                if not file.endswith(".java"):
                    continue
                path = os.path.join(root, file)
                with open(path, 'r', encoding='utf-8') as f:
                    content = f.read()
                
                # Replace classes
                def repl_class(m):
                    return class_map.get(m.group(0), m.group(0))
                content = re.sub(r'class_\d+', repl_class, content)
                
                # Replace fields
                def repl_field(m):
                    return field_map.get(m.group(0), m.group(0))
                content = re.sub(r'field_\d+', repl_field, content)
                
                # Replace methods
                def repl_method(m):
                    return method_map.get(m.group(0), m.group(0))
                content = re.sub(r'method_\d+', repl_method, content)
                
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(content)
                    
if __name__ == '__main__':
    print("Loading mappings...")
    c_map, f_map, m_map = load_mappings()
    print(f"Loaded {len(c_map)} classes, {len(f_map)} fields, {len(m_map)} methods.")
    print("Applying mappings...")
    apply_mappings(c_map, f_map, m_map)
    print("Done!")

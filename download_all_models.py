import urllib.request
import gzip
import json
import os

os.makedirs(r"A:\MCMDS\src\main\resources\assets\morecosmetics\models", exist_ok=True)

# 1. Download cosmetics.json
url_list = "http://dl.cosmeticsmod.com/morecosmetics/cosmetics.json"
req = urllib.request.Request(url_list, headers={'User-Agent': 'Mozilla/5.0'})
try:
    with urllib.request.urlopen(req) as response:
        cosmetics_list = json.loads(response.read().decode('utf-8'))
        with open(r"A:\MCMDS\src\main\resources\assets\morecosmetics\models\cosmetics.json", "w", encoding="utf-8") as f:
            json.dump(cosmetics_list, f, indent=2)
        print("Downloaded cosmetics.json")
except Exception as e:
    print("Failed to download cosmetics.json:", e)
    cosmetics_list = []

# 2. Download each .cos model
for item in cosmetics_list:
    cid = item['id']
    name = item['name']
    url = f"http://dl.cosmeticsmod.com/morecosmetics/cosmetics/{cid}.cos"
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        with urllib.request.urlopen(req) as response:
            with gzip.GzipFile(fileobj=response) as unzipped:
                data = json.loads(unzipped.read().decode('utf-8'))
                out_path = f"A:\\MCMDS\\src\\main\\resources\\assets\\morecosmetics\\models\\{cid}.json"
                with open(out_path, "w", encoding="utf-8") as f:
                    json.dump(data, f, indent=2)
                print(f"Downloaded {name} ({cid}.json)")
    except Exception as e:
        print(f"Failed to download {name} (id {cid}):", e)

print("Finished downloading all models.")

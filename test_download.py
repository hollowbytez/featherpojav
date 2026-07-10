import urllib.request
import gzip
import json

url = "http://dl.cosmeticsmod.com/morecosmetics/cosmetics/1.cos"
req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
try:
    with urllib.request.urlopen(req) as response:
        with gzip.GzipFile(fileobj=response) as unzipped:
            data = json.loads(unzipped.read().decode('utf-8'))
            print(json.dumps(data, indent=2)[:1000])
except Exception as e:
    print("Error:", e)

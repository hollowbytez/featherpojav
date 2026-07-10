import urllib.request
import zipfile
import os
import sys
import time

url = "https://aka.ms/download-jdk/microsoft-jdk-21-windows-x64.zip"
zip_path = "A:\\MCMDS\\jdk.zip"
extract_path = "A:\\MCMDS\\jdk"

# Clean up previous failed attempts
if os.path.exists(zip_path):
    try: os.remove(zip_path)
    except: pass
if os.path.exists(extract_path):
    import shutil
    try: shutil.rmtree(extract_path)
    except: pass
os.makedirs(extract_path, exist_ok=True)

print("Starting JDK download from Microsoft CDN...")
headers = {'User-Agent': 'Mozilla/5.0'}
req = urllib.request.Request(url, headers=headers)

max_retries = 3
retry_delay = 5

for attempt in range(max_retries):
    try:
        with urllib.request.urlopen(req) as response:
            total_size = int(response.headers.get('content-length', 0))
            downloaded = 0
            chunk_size = 1024 * 1024 # 1MB
            
            with open(zip_path, 'wb') as out_file:
                while True:
                    chunk = response.read(chunk_size)
                    if not chunk:
                        break
                    out_file.write(chunk)
                    downloaded += len(chunk)
                    
                    # Print progress percentage
                    if total_size > 0:
                        percent = (downloaded / total_size) * 100
                        sys.stdout.write(f"\rDownloading: {percent:.1f}% ({downloaded / (1024*1024):.1f}MB / {total_size / (1024*1024):.1f}MB)")
                    else:
                        sys.stdout.write(f"\rDownloading: {downloaded / (1024*1024):.1f}MB")
                    sys.stdout.flush()
            print("\nDownload finished successfully.")
            break
    except Exception as e:
        print(f"\nDownload attempt {attempt + 1} failed: {e}")
        if attempt < max_retries - 1:
            print(f"Retrying in {retry_delay} seconds...")
            time.sleep(retry_delay)
        else:
            print("Failed to download JDK after multiple attempts.")
            sys.exit(1)

print("Extracting JDK...")
try:
    with zipfile.ZipFile(zip_path, 'r') as zip_ref:
        zip_ref.extractall(extract_path)
    print("Extraction completed successfully.")
except Exception as e:
    print(f"Extraction failed: {e}")
    sys.exit(1)
finally:
    if os.path.exists(zip_path):
        try: os.remove(zip_path)
        except: pass

print("JDK is ready at: " + extract_path)

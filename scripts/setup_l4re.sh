#!/bin/bash

set -euo pipefail
clear
figlet "Auto setup..."
if [ -d  ~/IdeaProjects/StarshipOS ]; then
  rm -rfv ~/IdeaProjects/StarshipOS
fi
mkdir -p ~/IdeaProjects/StarshipOS
cd ~/IdeaProjects/StarshipOS || exit 1
# ---------------- CONFIG ---------------- #
BASE_URL="https://github.com/kernkonzept"
ARCHIVE="archive/refs/heads"

MODULES=(
  uvmm
  mk
  l4re-core
  l4_virtio-net-switch
  io
  fiasco
  emmc-driver
  bootstrap
  atkins
  demo-l4re-micro-hypervisor
  ada
  manifest
  zlib
  rtc
  cons
  l4virtio
  ham
  virtio-net
  tvmm
  tinit
  libblock-device
  acpica
  gnu-efi
  drivers-frst
  ahci-driver
  libfdt
  libvcpu
  readline
)

# Add OpenJDK separately
JDK_URL="https://github.com/openjdk/jdk/archive/refs/heads/master.tar.gz"
JDK_MODULE="jdk"
JDK_DIR="starship-os_${JDK_MODULE}"

# ---------------- DOWNLOAD ---------------- #
echo "⬇️  Downloading module tarballs..."
for module in "${MODULES[@]}"; do
  url="$BASE_URL/$module/$ARCHIVE/master.tar.gz"
  echo "📥 $module ..."
  curl -L --progress-bar "$url" -o "${module}.tar.gz"
done

# Download OpenJDK tarball
echo "📥 jdk ..."
curl -L --progress-bar "$JDK_URL" -o "${JDK_MODULE}.tar.gz"

# ---------------- EXTRACT ---------------- #
echo "📦 Extracting modules..."
for module in "${MODULES[@]}" "$JDK_MODULE"; do
  mkdir "starship-os_${module}"
  tar -xzf "${module}.tar.gz" -C "starship-os_${module}" --strip-components=1
done

# ---------------- CLEANUP ---------------- #
echo "🧹 Removing tarballs..."
rm -f *.tar.gz

# ---------------- MAKE HELP & README ---------------- #
echo "📖 Generating make help and README.md files..."
for dir in starship-os_*; do
  module="${dir#starship-os_}"

  echo "📚 $module ..."
  (
    cd "$dir"
    make --help > "./${module}-make.txt" 2>&1 || echo "⚠️ make --help failed in $dir"

    if [[ -f README.md ]]; then
      echo -e "\n---\nAppend from script on $(date)" >> README.md
    fi

    echo "# $module module" >> README.md
    echo "Auto-generated placeholder README for module \`$module\`." >> README.md
  )
done

# ---------------- MAKE B=build ---------------- #
echo "🔧 Building modules with make B=build ..."
for dir in starship-os_*; do
  [[ "$dir" == "$JDK_DIR" ]] && continue

  echo "🚧 Building in $dir ..."
  (
    cd "$dir" && make B=build
  ) || echo "⚠️ Build failed in $dir — continuing..."
done

# ---------------- MODULE SCRIPTS ---------------- #
echo "📂 Creating scripts/ in each module..."
for dir in starship-os_*; do
  scripts_dir="${dir}/scripts"
  mkdir -p "$scripts_dir"

  module="${dir#starship-os_}"
  for script in clean compile package install; do
    script_file="${scripts_dir}/${script}.sh"
    cat > "$script_file" <<EOF
#!/bin/sh
set -euo pipefail

figlet "StarshipOS_${module}"
echo "Executing ${script}.sh"
EOF
    chmod +x "$script_file"
  done
done

# ---------------- ROOT SCRIPTS ---------------- #
echo "🛠️  Creating scripts/ directory in root project..."

root_scripts_dir="./scripts"
mkdir -p "$root_scripts_dir"

for script in clean compile package install; do
  script_file="$root_scripts_dir/${script}.sh"
  cat > "$script_file" <<EOF
#!/bin/sh
set -euo pipefail

figlet "StarshipOS Root"
echo "Executing ${script}.sh"
EOF
  chmod +x "$script_file"
  echo "✅ Created $script_file"
done

# ---------------- DONE ---------------- #
echo -e "\n✅ Script completed!"

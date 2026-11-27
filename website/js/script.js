document.addEventListener('DOMContentLoaded', () => {
    
    // =========================================
    // 1. Mobile Navigation Toggle
    // =========================================
    const mobileMenuBtn = document.getElementById('mobile-menu-btn');
    const mobileMenu = document.getElementById('mobile-menu');
    const mobileLinks = document.querySelectorAll('.mobile-link');

    mobileMenuBtn.addEventListener('click', () => {
        mobileMenu.classList.toggle('hidden');
    });

    // Close mobile menu when a link is clicked
    mobileLinks.forEach(link => {
        link.addEventListener('click', () => {
            mobileMenu.classList.add('hidden');
        });
    });

    // =========================================
    // 2. Dynamic Footer Year
    // =========================================
    document.getElementById('year').textContent = new Date().getFullYear();

    // =========================================
    // 3. Scroll Reveal Animations
    // =========================================
    const revealElements = document.querySelectorAll('.reveal');
    
    const revealObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('active');
                observer.unobserve(entry.target); // Stop observing once revealed
            }
        });
    }, {
        root: null,
        threshold: 0.1,
        rootMargin: "0px 0px -50px 0px"
    });

    revealElements.forEach(el => revealObserver.observe(el));

    // =========================================
    // 4. Back to Top Button
    // =========================================
    const backToTopBtn = document.getElementById('back-to-top');
    
    window.addEventListener('scroll', () => {
        if (window.scrollY > 500) {
            backToTopBtn.classList.remove('opacity-0', 'invisible');
            backToTopBtn.classList.add('opacity-100', 'visible');
        } else {
            backToTopBtn.classList.add('opacity-0', 'invisible');
            backToTopBtn.classList.remove('opacity-100', 'visible');
        }
    });

    backToTopBtn.addEventListener('click', () => {
        window.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
    });

    // =========================================
    // 5. GitHub API Integration (Latest Release)
    // =========================================
    const apiUrl = 'https://api.github.com/repos/adityasaini404/PadhteRaho-Apk/releases/latest';
    
    // UI Elements
    const loadingState = document.getElementById('download-loading');
    const successState = document.getElementById('download-success');
    const errorState = document.getElementById('download-error');
    const heroVersionInfo = document.getElementById('hero-version-info');
    
    // Dynamic fields
    const downloadBtn = document.getElementById('apk-download-btn');
    const versionSpan = document.getElementById('apk-version');
    const sizeSpan = document.getElementById('apk-size');
    const dateSpan = document.getElementById('apk-date');

    function formatBytes(bytes, decimals = 2) {
        if (!+bytes) return '0 MB';
        const k = 1024;
        const dm = decimals < 0 ? 0 : decimals;
        const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return `${parseFloat((bytes / Math.pow(k, i)).toFixed(dm))} ${sizes[i]}`;
    }

    function formatDate(dateString) {
        const options = { year: 'numeric', month: 'short', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    }

    async function fetchLatestRelease() {
        try {
            const response = await fetch(apiUrl);
            
            if (!response.ok) {
                throw new Error(`GitHub API error: ${response.status}`);
            }
            
            const data = await response.json();
            
            // Find the APK asset
            const apkAsset = data.assets.find(asset => asset.name.endsWith('.apk'));
            
            // Update Hero Version Info
            heroVersionInfo.innerHTML = `
                <span class="flex h-2 w-2 relative">
                    <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"></span>
                    <span class="relative inline-flex rounded-full h-2 w-2 bg-green-500"></span>
                </span>
                Latest version: <strong class="text-gray-800">${data.tag_name}</strong>
            `;

            if (apkAsset) {
                // Populate Success State
                downloadBtn.href = apkAsset.browser_download_url;
                versionSpan.textContent = data.tag_name;
                sizeSpan.textContent = formatBytes(apkAsset.size);
                dateSpan.textContent = formatDate(data.published_at);
                
                // Toggle UI states
                loadingState.classList.add('hidden');
                loadingState.classList.remove('flex');
                successState.classList.remove('hidden');
                successState.classList.add('flex');
            } else {
                // No APK asset found in release, fallback to release page
                throw new Error("No .apk asset found in the latest release.");
            }

        } catch (error) {
            console.error("Error fetching release:", error);
            
            // Show Error State
            loadingState.classList.add('hidden');
            loadingState.classList.remove('flex');
            errorState.classList.remove('hidden');
            errorState.classList.add('flex');
            
            // Update Hero Info to show error/fallback
            heroVersionInfo.innerHTML = `
                <span class="text-red-500"><i class="bi bi-exclamation-circle"></i> Could not fetch latest version</span>
            `;
        }
    }

    // Initiate Fetch
    fetchLatestRelease();
});

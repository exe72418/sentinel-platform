/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'sentinel-bg': '#0F172A',
        'sentinel-card': '#1E293B',
        'sentinel-text': '#E2E8F0',
        'sentinel-green': '#10B981', // Neon green for AI
        'sentinel-red': '#EF4444',   // Red for errors
        'sentinel-accent': '#3B82F6',
      },
    },
  },
  plugins: [],
}

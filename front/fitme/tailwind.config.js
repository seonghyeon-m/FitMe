/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts,tsx}', 'node_modules/flowbite-react/lib/esm/**/*.js'],
  theme: {
    extend: {
      aspectRatio: {
        iphone: '18 / 32',
      },
    },
  },
  plugins: [],
};

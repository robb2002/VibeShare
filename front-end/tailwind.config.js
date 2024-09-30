/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      backgroundImage: {
        "custom-gradient":
          "linear-gradient(to right, #003366, #004080, #0059b3, #0073e6)",
      },
      colors: {
        primary: "#1E40AF", // Customize primary colors here
        secondary: "#10B981",
      },
    },
  },
  plugins: [],
};

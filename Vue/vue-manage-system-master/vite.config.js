import vue from '@vitejs/plugin-vue'

export default {
    base: './',
    plugins: [vue()],
    chunkSizeWarningLimit:1500,
    build:{
        publicPath: '',
        chunkSizeWarningLimit:1500,
        rollupOptions: {
            output:{
                manualChunks(id) {
                    if (id.includes('node_modules')) {
                        return id.toString().split('node_modules/')[1].split('/')[0].toString();
                    }
                }
            }
        },
    },
    optimizeDeps: {
        include: ['schart.js']
    }
}
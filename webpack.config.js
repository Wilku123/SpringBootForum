const WebpackNotifierPlugin = require('webpack-notifier');
const webpack = require('webpack');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");

module.exports = {
    entry: {
        app: ['./src/main/js/Index.jsx']
    },
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    resolve: {
        // Look for modules in .ts(x) files first, then .js(x)
        extensions: ['.ts', '.tsx', '.js', '.jsx']
        // Add 'src' to our modulesDirectories, as all our app code will live in there, so Webpack should look in there for modules
    },
    module: {
        rules: [
            {
                test: /\.jsx$/,
                loader: "babel-loader",
                exclude: /(node_modules)/
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: "style-loader",
                    use: "css-loader?importLoader=1&modules&localIdentName=[path]___[name]__[local]___[hash:base64:5]"
                })
            },
            {
                test: /\.svg$/,
                loader: 'svg-inline-loader'
            }
        ],

    },
    plugins: [
        new ExtractTextPlugin("./src/main/resources/static/built/styles.css"),
        // Set up the notifier plugin - you can remove this (or set alwaysNotify false) if desired
        new WebpackNotifierPlugin({alwaysNotify: true}),
        //TODO usun to pozniej kiedys tam
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: JSON.stringify('production')
            }
        }),
        new UglifyJsPlugin({
            uglifyOptions: {
                ie8: false,
                ecma: 8,
                output: {
                    comments: false,
                    beautify: false
                },
                compress: true,
                warnings: false
            }
        })
        //TODO ^ to usun
    ],
    watch: true

};
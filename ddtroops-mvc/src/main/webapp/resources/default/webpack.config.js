const path = require("path");
var webpack = require("webpack");


module.exports = {
    entry: {
        app: "./src/app.js",
    },
    output: {
        path: path.join(__dirname, "out"), // All of the output filles will be generated to out directory
        filename: "[name].out.js"
    },
    module: {
        loaders: [
            { test: /\.scss$/, loaders: ['style', 'css', 'postcss', 'sass'] },
            { test: /bootstrap\/dist\/js\/umd\//, loader: 'imports?jQuery=jquery' },
            { test: /\.css$/, loader: "style!css" },
            { test: /\.(png|woff|woff2|eot|ttf|svg)$/, loader: 'url-loader?limit=100000' },
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel',
                query: {
                    presets: ['stage-1', 'es2015', 'react'],
                    plugins: ['transform-class-properties', 'transform-decorators-legacy']
                }
            }
        ]
    },
    plugins: [
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("develop")
            }
        }),
        new webpack.ProvidePlugin({
            jQuery: 'jquery',
            $: 'jquery',
            jquery: 'jquery'
        })
    ],
    resolve: {
        extensions: ['', '.js', '.jsx']
    }
};
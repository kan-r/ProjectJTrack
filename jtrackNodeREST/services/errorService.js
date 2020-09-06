const invalidData = (err) => {
    return {
        error: 'invalid_data',
        message: err,
        timestamp: new Date()
    };
}

module.exports = invalidData;
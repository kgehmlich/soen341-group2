//
// Copyright 2017 Amazon.com, Inc. or its affiliates (Amazon). All Rights Reserved.
//
// Code generated by AWS Mobile Hub. Amazon gives unlimited permission to 
// copy, distribute and modify it.
//
// Source code generated from template: aws-my-sample-app-android v0.15
//
package com.amazonaws.mobile.content;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;

/**
 * Represents metadata for a file object in S3. Use ContentManager.getContent(...) to retrieve the
 * file contents.
 */
public class S3ContentSummary implements ContentItem {
    private final String path;
    private final long size;
    private final long lastModifiedTime;
    private ContentState contentState;

    public S3ContentSummary(final String dirName) {
        this.path = dirName;
        this.size = 0;
        this.lastModifiedTime = 0;
        contentState = ContentState.REMOTE_DIRECTORY;
    }

    public S3ContentSummary(final S3ObjectSummary objectSummary, final String filePath) {
        lastModifiedTime = objectSummary.getLastModified().getTime();
        size = objectSummary.getSize();
        path = filePath;
        this.contentState = ContentState.REMOTE;
    }

    /** {@inheritDoc} */
    @Override
    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    /** {@inheritDoc} */
    @Override
    public String getFilePath() {
        return path;
    }

    /** {@inheritDoc} */
    @Override
    public long getSize() {
        return size;
    }

    /** {@inheritDoc} */
    @Override
    public ContentState getContentState() {
        return contentState;
    }

    /** {@inheritDoc} */
    @Override
    public void setContentState(final ContentState contentState) {
        this.contentState = contentState;
    }

    /**
     * @return null because this object only contains metadata. In order to
     * get the file from local cache, you must first tell the ContentManager to
     * download the file contents with a getContent call.
     */
    @Override
    public File getFile() {
        return null;
    }
}

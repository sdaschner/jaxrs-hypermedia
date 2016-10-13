package com.sebastian_daschner.jaxrs_hypermedia.siren_siren4javaee.model;

import java.net.URI;

public class BookTeaser {

    private URI uri;
    private String name;
    private String author;

    public URI getUri() {
        return uri;
    }

    public void setUri(final URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookTeaser{" +
                "uri='" + uri + '\'' +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

}

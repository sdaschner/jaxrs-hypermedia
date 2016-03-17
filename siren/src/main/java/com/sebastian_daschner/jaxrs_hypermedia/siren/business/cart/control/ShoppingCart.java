package com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.control;

import com.sebastian_daschner.jaxrs_hypermedia.siren.business.books.control.PriceCalculator;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Singleton
public class ShoppingCart {

    @Inject
    PriceCalculator priceCalculator;

    private Set<BookSelection> selections;
    private int nextSelectionId = 1;

    @PostConstruct
    public void initSelections() {
        selections = new HashSet<>();
    }

    @Lock(LockType.WRITE)
    public void addBookSelection(BookSelection selection) {
        final Optional<BookSelection> existentSelection = selections.stream().filter(s -> s.getBook().equals(selection.getBook())).findFirst();

        if (existentSelection.isPresent()) {
            final BookSelection bookSelection = existentSelection.get();
            bookSelection.setQuantity(bookSelection.getQuantity() + selection.getQuantity());
            updatePrice(bookSelection);
        } else {
            updatePrice(selection);
            selections.add(selection);
            selection.setId(nextSelectionId++);
        }
    }

    @Lock(LockType.WRITE)
    public void updateBookSelection(long selectionId, int quantity) {
        final BookSelection selection = selections.stream()
                .filter(s -> s.getId() == selectionId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No selection found"));

        selection.setQuantity(quantity);
        updatePrice(selection);

        if (quantity == 0)
            selections.remove(selection);
    }

    @Lock(LockType.WRITE)
    public void clear() {
        selections.clear();
    }

    private void updatePrice(BookSelection selection) {
        selection.setPrice(priceCalculator.calculatePrice(selection));
    }

    @Lock(LockType.READ)
    public Set<BookSelection> getSelections() {
        return Collections.unmodifiableSet(selections);
    }

}

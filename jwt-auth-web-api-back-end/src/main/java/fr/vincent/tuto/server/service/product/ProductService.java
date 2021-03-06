/*
 * ----------------------------------------------
 * Projet ou Module : jwt-auth-web-api-back-end
 * Nom de la classe : ProductService.java
 * Date de création : 31 janv. 2021
 * Heure de création : 06:28:58
 * Package : fr.vincent.tuto.server.service.product.impl
 * Auteur : Vincent Otchoun
 * Copyright © 2021 - All rights reserved.
 * ----------------------------------------------
 */
package fr.vincent.tuto.server.service.product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import fr.vincent.tuto.common.exception.CustomAppException;
import fr.vincent.tuto.server.dao.ProductDAO;
import fr.vincent.tuto.server.model.po.Product;
import fr.vincent.tuto.server.service.contract.IProductService;
import fr.vincent.tuto.server.util.ServerUtil;

/**
 * Service des fonctionnalités de gestion des informations des produits du SI.
 * 
 * @author Vincent Otchoun
 */
@Service(value = "productService")
@Transactional
public class ProductService implements IProductService
{
    private static final String SAVE_MESSAGE = "Erreur lors de la sauvegarde en base de donnnées des informations d'un produits.";
    private static final String FIND_BY_ID_MESSAGE = "Erreur recherche des informations d'un produit par identifiant.";
    private static final String FIND_BY_NAME_MESSAGE = "Erreur recherche des informations d'un produit par son nom.";

    private final ProductDAO productDAO;

    /**
     * Constructeur avec injection du DAO des opérations de gestion des produits dans le SI.
     * 
     * @param pProductDAO le dépôt Spring Data JPA pour l'entité {@link Product}.
     */
    @Autowired
    public ProductService(final ProductDAO pProductDAO)
    {
        this.productDAO = pProductDAO;
    }

    /**
     * Enregistrer les informations d'un produit dans le système d'informations.
     * 
     * @param pProduct les informations du produit à enregistrer.
     * @return les informations du produit enregistré.
     */
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    @Override
    public Product createProduct(Product pProduct)
    {
        try
        {
            // Traitement métier pour le calcul et affectation du prix total de la quantité commandée
            final var quantite = pProduct.getQuantity();
            final var prixUnitaire = pProduct.getUnitPrice();
            final var prixTotal = new BigDecimal(quantite.longValue()).multiply(prixUnitaire);
            pProduct.setPrice(prixTotal);

            final var product = this.productDAO.save(pProduct);
            Assert.notNull(product, SAVE_MESSAGE);
            return product;
        }
        catch (Exception e)
        {
            throw new CustomAppException(SAVE_MESSAGE, e);
        }
    }

    /**
     * Obtenir les informations d'un produit à partir de son identifiant.
     * 
     * @param pProductId identifiant du produit recherché.
     * @return le produit recherché.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Optional<Product> getProductById(Long pProductId)
    {
        return Optional.ofNullable(this.productDAO.findById(pProductId))//
        .filter(Optional::isPresent)//
        .orElseThrow(() -> new CustomAppException(FIND_BY_ID_MESSAGE));//
    }

    /**
     * Obtenir les informations d'un produit à partir de son nom.
     * 
     * @param pName le nom du produit recherché.
     * @return les informations du produit correspondant aux critères de recherche.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Optional<Product> getProductByName(String pName)
    {
        return Optional.ofNullable(this.productDAO.findOneByName(pName))//
        .filter(Optional::isPresent)//
        .orElseThrow(() -> new CustomAppException(FIND_BY_NAME_MESSAGE));
    }

    /**
     * Obtenir les informations d'un produit à partir de son nom en ignorant la casse.
     * 
     * @param pName le nom du produit recherché.
     * @return les informations du produit correspondant aux critères de recherche.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Optional<Product> getProductByNameIgnoreCase(String pName)
    {
        return Optional.ofNullable(this.productDAO.findOneByNameIgnoreCase(pName))//
        .filter(Optional::isPresent)//
        .orElseThrow(() -> new CustomAppException(FIND_BY_NAME_MESSAGE));
    }

    /**
     * Indiquer l'existence des informations d'un produit dans le système d'informations à partir de son nom.
     * 
     * @param pName le nom du produi recherché.
     * @return true si le produit existe, false sinon.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Boolean existsProductByName(String pName)
    {
        return this.productDAO.existsByName(pName);
    }

    /**
     * Obtenir une liste paginée de produits selon l'état en base de données (actif ou non).
     * 
     * @param productIsActive état des des produits à remonter.
     * @param pPageable       pagination de la liste (index de la page, nombre d'éléments dans la page à retrourner).
     * @return la liste paginée des informations des produits correspondant.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Page<Product> getProductsByIsActive(Boolean productIsActive, Pageable pPageable)
    {
        return this.productDAO.findAllByIsActive(productIsActive, pPageable);
    }

    /**
     * Obtenir une liste de produits selon l'état en base de données (actif ou non).
     * 
     * @param productIsActive état des des produits à remonter
     * @return la liste des informations des produits correspondant.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Collection<Product> getProductsByIsActive(Boolean productIsActive)
    {
        return this.productDAO.findAllByIsActive(productIsActive);
    }

    /**
     * Obtenir la liste des informations des produits dans le système d'informations.
     * 
     * @return la liste des informations des produits.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Collection<Product> getProducts()
    {
        return this.productDAO.findAll();
    }

    /**
     * Obtenez une liste de produits filtrée avec un nom de produit correspondant à la requête donnée.
     * 
     * @param pQuery le modèle de requête donné.
     * @return la liste filtrée des produits correspondant aux critères de recherche.
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR','ROLE_USER')")
    @Override
    public Collection<Product> getFilteredProducts(String pQuery)
    {
        final var products = (List<Product>) this.getProducts();

        // Retourner la liste filtrée sur les nom des produits qui 'match' avec le pattern fourni
        return Optional.ofNullable(products.stream()//
        .filter(Objects::nonNull)//
        .filter(product -> ServerUtil.strCaseInsentitive(product.getName(), pQuery))//
        .distinct()//
        .collect(Collectors.toList())//
        )//
        .orElseGet(Collections::emptyList)//
        ;
    }

    /**
     * Supprimer les informations d'un produit de la base de données.
     * 
     * @param pProductId identifiant du produit à supprimer du SI.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    @Override
    public void deleteProduct(Long pProductId)
    {
        try
        {
            this.getProductById(pProductId)//
            .ifPresent(this.productDAO::delete);
        }
        catch (Exception e)
        {
            throw new CustomAppException(e);
        }
    }

    /**
     * Mettre à jour les informations d'un produit du SI.
     * 
     * @param pProductId identifiant du produit à mettre à jour.
     * @param pProduct   les informations du produit à mettre à jour.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    @Override
    public void updateProduct(Long pProductId, Product pProduct)
    {
        try
        {
            this.getProductById(pProductId)//
            .ifPresent(product -> {
                final Long id = product.getId();
                pProduct.setId(id);
                this.createProduct(pProduct);
            });
        }
        catch (Exception e)
        {
            throw new CustomAppException(e);
        }
    }
}

import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBrand } from 'app/shared/model/brand.model';
import { getEntities as getBrands } from 'app/entities/brand/brand.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './production.reducer';
import { IProduction } from 'app/shared/model/production.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { FilePicker } from 'react-file-picker'

export interface IProductionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionUpdate = (props: IProductionUpdateProps) => {
  const [brandId, setBrandId] = useState('0');
  const [categoryId, setCategoryId] = useState('0');
  const [arrayFiles , setArrayFiles] = useState([]);
  const [selectedFile, setSelectedFile] = useState('');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { productionEntity, brands, categories, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/production' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getBrands();
    props.getCategories();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  /* eslint-disable */
  const uploadImage = (FileObject) => {
    console.log("FIleUpload",FileObject);
    setArrayFiles(arrayFiles => [...arrayFiles,FileObject]);
    console.log("arrayFiles", arrayFiles);
  } 

  const saveEntity = (event, errors, values) => {
    const listImage = []
    if (errors.length === 0) {
      const listImage = []
      const entity = {
        ...productionEntity,
        ...values,
      };
      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="tinhocdanangApp.production.home.createOrEditLabel">
            <Translate contentKey="tinhocdanangApp.production.home.createOrEditLabel">Create or edit a Production</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : productionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="production-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="production-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="production-name">
                  <Translate contentKey="tinhocdanangApp.production.name">Name</Translate>
                </Label>
                <AvField
                  id="production-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="production-price">
                  <Translate contentKey="tinhocdanangApp.production.price">Price</Translate>
                </Label>
                <AvField
                  id="production-price"
                  type="string"
                  className="form-control"
                  name="price"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="production-description">
                  <Translate contentKey="tinhocdanangApp.production.description">Description</Translate>
                </Label>
                <AvField id="production-description" type="text" name="description" />
              </AvGroup>

               <FilePicker
                  extensions = {['jpg','jpeg','png']}
                  onChange={FileObject => uploadImage(FileObject)}
                  onError={errMsg => console.warn(errMsg)}
                  >
                    <div>
                      <Label id="cardimageLabel" for="card-type-cardImage">
                        <Translate contentKey="ccpTatemApp.cardType.cardImage">Image</Translate>
                      </Label>
                      
                      <Button type="button" color="info" className="ml-1" >
                        <FontAwesomeIcon icon="save" size="sm" />
                      </Button>
                      <AvInput enctype="multipart/form-data" id = "card-type-cardImage" type="hidden" value={arrayFiles}  name="imageFile" />
                    </div>
                  </FilePicker>
              <AvGroup>
                <Label id="salePriceLabel" for="production-salePrice">
                  <Translate contentKey="tinhocdanangApp.production.salePrice">Sale Price</Translate>
                </Label>
                <AvField id="production-salePrice" type="string" className="form-control" name="salePrice" />
              </AvGroup>
              <AvGroup>
                <Label id="quantityLabel" for="production-quantity">
                  <Translate contentKey="tinhocdanangApp.production.quantity">Quantity</Translate>
                </Label>
                <AvField id="production-quantity" type="string" className="form-control" name="quantity" />
              </AvGroup>
              <AvGroup>
                <Label id="conditionLabel" for="production-condition">
                  <Translate contentKey="tinhocdanangApp.production.condition">Condition</Translate>
                </Label>
                <AvField id="production-condition" type="string" className="form-control" name="condition" />
              </AvGroup>
              <AvGroup>
                <Label id="originLabel" for="production-origin">
                  <Translate contentKey="tinhocdanangApp.production.origin">Origin</Translate>
                </Label>
                <AvField id="production-origin" type="text" name="origin" />
              </AvGroup>
              <AvGroup>
                <Label id="configurationLabel" for="production-configuration">
                  <Translate contentKey="tinhocdanangApp.production.configuration">Configuration</Translate>
                </Label>
                <AvField id="production-configuration" type="text" name="configuration" />
              </AvGroup>
              <AvGroup>
                <Label id="creationDateLabel" for="production-creationDate">
                  <Translate contentKey="tinhocdanangApp.production.creationDate">Creation Date</Translate>
                </Label>
                <AvField id="production-creationDate" type="date" className="form-control" name="creationDate" />
              </AvGroup>
              <AvGroup>
                <Label for="production-brand">
                  <Translate contentKey="tinhocdanangApp.production.brand">Brand</Translate>
                </Label>
                <AvInput
                  id="production-brand"
                  type="select"
                  className="form-control"
                  name="brand.id"
                  value={isNew ? brands[0] && brands[0].id : productionEntity.brand?.id}
                  required
                >
                  {brands
                    ? brands.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="production-category">
                  <Translate contentKey="tinhocdanangApp.production.category">Category</Translate>
                </Label>
                <AvInput
                  id="production-category"
                  type="select"
                  className="form-control"
                  name="category.id"
                  value={isNew ? categories[0] && categories[0].id : productionEntity.category?.id}
                  required
                >
                  {categories
                    ? categories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/production" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  brands: storeState.brand.entities,
  categories: storeState.category.entities,
  productionEntity: storeState.production.entity,
  loading: storeState.production.loading,
  updating: storeState.production.updating,
  updateSuccess: storeState.production.updateSuccess,
});

const mapDispatchToProps = {
  getBrands,
  getCategories,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionUpdate);

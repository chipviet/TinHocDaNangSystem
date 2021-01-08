import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './production.reducer';
import { IProduction } from 'app/shared/model/production.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProductionDetail = (props: IProductionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { productionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="tinhocdanangApp.production.detail.title">Production</Translate> [<b>{productionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="tinhocdanangApp.production.name">Name</Translate>
            </span>
          </dt>
          <dd>{productionEntity.name}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="tinhocdanangApp.production.price">Price</Translate>
            </span>
          </dt>
          <dd>{productionEntity.price}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="tinhocdanangApp.production.description">Description</Translate>
            </span>
          </dt>
          <dd>{productionEntity.description}</dd>
          <dt>
            <span id="imageURL">
              <Translate contentKey="tinhocdanangApp.production.imageURL">Image URL</Translate>
            </span>
          </dt>
          <dd>{productionEntity.imageURL}</dd>
          <dt>
            <span id="salePrice">
              <Translate contentKey="tinhocdanangApp.production.salePrice">Sale Price</Translate>
            </span>
          </dt>
          <dd>{productionEntity.salePrice}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="tinhocdanangApp.production.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{productionEntity.quantity}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="tinhocdanangApp.production.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{productionEntity.condition}</dd>
          <dt>
            <span id="origin">
              <Translate contentKey="tinhocdanangApp.production.origin">Origin</Translate>
            </span>
          </dt>
          <dd>{productionEntity.origin}</dd>
          <dt>
            <span id="configuration">
              <Translate contentKey="tinhocdanangApp.production.configuration">Configuration</Translate>
            </span>
          </dt>
          <dd>{productionEntity.configuration}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="tinhocdanangApp.production.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {productionEntity.creationDate ? (
              <TextFormat value={productionEntity.creationDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.production.brand">Brand</Translate>
          </dt>
          <dd>{productionEntity.brand ? productionEntity.brand.id : ''}</dd>
          <dt>
            <Translate contentKey="tinhocdanangApp.production.category">Category</Translate>
          </dt>
          <dd>{productionEntity.category ? productionEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/production" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/production/${productionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ production }: IRootState) => ({
  productionEntity: production.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProductionDetail);
